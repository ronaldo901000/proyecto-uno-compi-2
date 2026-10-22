package com.ronaldo.cd3.compiler.api.services.generador.riscv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Ensamblador RISC-V RV32 mínimo. Convierte un texto ensamblador (formato
 * generado por {@link EmisorRiscV} y {@link RuntimeRiscV}) en un binario
 * ELF32 ejecutable por {@code qemu-riscv32}. No depende de toolchain C.
 *
 * <p>Soporta el subconjunto de instrucciones utilizado por el backend: carga/
 * almacenamiento (lw, sw, lb, sb), aritmética entera (add, sub, mul, div,
 * addi, andi, xori, slt, and, or), saltos (j, jal, jalr, ret, bnez), carga de
 * direcciones (la, li), punto flotante simple precisión (fadd.s, fsub.s,
 * fmul.s, fdiv.s, frem.s, fmv.w.x, fmv.x.w, fcvt.s.w, fcvt.w.s, feq.s, flt.s,
 * fle.s) y ecall.</p>
 */
public class EnsambladorRiscV32 {

    private static final int REG_ZERO = 0;
    private static final int REG_RA = 1;
    private static final int REG_SP = 2;
    private static final int REG_T0 = 5;
    private static final int REG_T1 = 6;
    private static final int REG_T2 = 7;
    private static final int REG_S0 = 8;
    private static final int REG_A0 = 10;
    private static final int REG_A1 = 11;
    private static final int REG_A7 = 17;
    private static final int REG_T6 = 31;

    private static final int BASE_MEMORIA = 0x00010000;
    private static final int TAMAÑO_HEADERS = 84; // ELF header (52) + program header (32)

    private final String texto;
    private final Map<String, Integer> etiquetas = new LinkedHashMap<>();
    private final List<Byte> codigo = new ArrayList<>();
    private int seccionDatosInicio;
    private boolean enDatos;

    private final Map<String, Integer> registros = new HashMap<>();

    public EnsambladorRiscV32(String texto) {
        this.texto = texto;
        iniciarRegistros();
    }

    private void iniciarRegistros() {
        registros.put("zero", 0);
        registros.put("ra", 1);
        registros.put("sp", 2);
        registros.put("gp", 3);
        registros.put("tp", 4);
        registros.put("t0", 5);
        registros.put("t1", 6);
        registros.put("t2", 7);
        registros.put("s0", 8);
        registros.put("s1", 9);
        registros.put("a0", 10);
        registros.put("a1", 11);
        registros.put("a2", 12);
        registros.put("a3", 13);
        registros.put("a4", 14);
        registros.put("a5", 15);
        registros.put("a6", 16);
        registros.put("a7", 17);
        registros.put("s2", 18);
        registros.put("s3", 19);
        registros.put("s4", 20);
        registros.put("s5", 21);
        registros.put("s6", 22);
        registros.put("s7", 23);
        registros.put("s8", 24);
        registros.put("s9", 25);
        registros.put("s10", 26);
        registros.put("s11", 27);
        registros.put("t3", 28);
        registros.put("t4", 29);
        registros.put("t5", 30);
        registros.put("t6", 31);
        registros.put("fp", 8); // alias de s0
    }

    /**
     * Ensambla todo el texto y devuelve el ELF32 en bytes.
     * {@code entryPoint} se establece a la dirección de la etiqueta
     * {@code main} (o {@code __entrada_main}).
     */
    public byte[] ensamblar() {
        List<String> lineas = dividirLineas(texto);
        pase1(lineas);
        pase2(lineas);
        return construirElf();
    }

    public int getEntryPoint() {
        Integer e = etiquetas.get("main");
        if (e != null) {
            return e;
        }
        e = etiquetas.get("__entrada_main");
        if (e != null) {
            return e;
        }
        return 0x00010000;
    }

    // ------------------------------------------------------------------
    // Pase 1: recolectar etiquetas
    // ------------------------------------------------------------------

    private void pase1(List<String> lineas) {
        int emitido = 0;
        enDatos = false;
        for (String linea : lineas) {
            String s = linea.trim();
            if (s.isEmpty()) {
                continue;
            }
            if (s.equals(".text")) {
                enDatos = false;
                continue;
            }
            if (s.equals(".data")) {
                enDatos = true;
                continue;
            }
            if (s.endsWith(":") && !s.startsWith(".")) {
                String nombre = s.substring(0, s.length() - 1).trim();
                if (!etiquetas.containsKey(nombre)) {
                    etiquetas.put(nombre, direccionDe(emitido));
                }
                continue;
            }
            if (enDatos) {
                emitido += tamanioDirectivaDatos(s);
                continue;
            }
            emitido += 4; // instrucción
        }
    }

    private int direccionDe(int emitido) {
        return BASE_MEMORIA + emitido;
    }

    private int tamanioDirectivaDatos(String s) {
        if (s.startsWith(".asciz")) {
            int ini = s.indexOf('"');
            int fin = s.lastIndexOf('"');
            if (ini >= 0 && fin > ini) {
                return (fin - ini - 1) + 1; // contenido + nulo
            }
            return 1;
        }
        if (s.startsWith(".float")) {
            return 4;
        }
        if (s.startsWith(".word")) {
            return 4;
        }
        if (s.startsWith(".space") || s.startsWith(".zero")) {
            return parseInmediato(s.substring(s.indexOf(' ') + 1).trim());
        }
        if (s.startsWith(".align")) {
            return 0; // se maneja en pase 2 con relleno
        }
        return 0;
    }

    // ------------------------------------------------------------------
    // Pase 2: emitir bytes
    // ------------------------------------------------------------------

    private void pase2(List<String> lineas) {
        codigo.clear();
        enDatos = false;
        for (String linea : lineas) {
            String s = linea.trim();
            if (s.isEmpty()) {
                continue;
            }
            if (s.equals(".text")) {
                enDatos = false;
                continue;
            }
            if (s.equals(".data")) {
                enDatos = true;
                continue;
            }
            if (s.endsWith(":") && !s.startsWith(".")) {
                continue; // ya registrada
            }
            if (enDatos) {
                emitirDirectivaDatos(s);
                continue;
            }
            emitirInstruccion(s);
        }
    }

    private void emitirDirectivaDatos(String s) {
        if (s.startsWith(".align")) {
            int exp = 0;
            try {
                exp = Integer.parseInt(s.substring(s.indexOf(' ') + 1).trim());
            } catch (Exception ex) {
                exp = 2;
            }
            int alineacion = 1 << exp;
            while ((codigo.size() % alineacion) != 0) {
                codigo.add((byte) 0);
            }
            return;
        }
        if (s.startsWith(".asciz")) {
            int ini = s.indexOf('"');
            int fin = s.lastIndexOf('"');
            String contenido = (ini >= 0 && fin > ini) ? s.substring(ini + 1, fin) : "";
            for (int i = 0; i < contenido.length(); i++) {
                codigo.add((byte) contenido.charAt(i));
            }
            codigo.add((byte) 0);
            return;
        }
        if (s.startsWith(".float")) {
            String valor = s.substring(s.indexOf(' ') + 1).trim();
            int bits = Float.floatToIntBits(Float.parseFloat(valor));
            agregarPalabra(bits);
            return;
        }
        if (s.startsWith(".word")) {
            String valor = s.substring(s.indexOf(' ') + 1).trim();
            int dir = resolverDireccion(valor);
            agregarPalabra(dir);
            return;
        }
        if (s.startsWith(".space") || s.startsWith(".zero")) {
            int n = parseInmediato(s.substring(s.indexOf(' ') + 1).trim());
            for (int i = 0; i < n; i++) {
                codigo.add((byte) 0);
            }
            return;
        }
    }

    private int resolverDireccion(String valor) {
        if (etiquetas.containsKey(valor)) {
            return etiquetas.get(valor);
        }
        try {
            return Integer.decode(valor);
        } catch (Exception ex) {
            return 0;
        }
    }

    private void agregarPalabra(int valor) {
        codigo.add((byte) (valor & 0xFF));
        codigo.add((byte) ((valor >> 8) & 0xFF));
        codigo.add((byte) ((valor >> 16) & 0xFF));
        codigo.add((byte) ((valor >> 24) & 0xFF));
    }

    // ------------------------------------------------------------------
    // Emisión de instrucciones
    // ------------------------------------------------------------------

    private void emitirInstruccion(String s) {
        String mnem = obtenerMnemotecnico(s);
        String resto = obtenerResto(s);
        switch (mnem) {
            case "add": emitirR(0x33, 0x00, 0x0, resto); break;
            case "sub": emitirR(0x33, 0x20, 0x0, resto); break;
            case "and": emitirR(0x33, 0x00, 0x7, resto); break;
            case "or": emitirR(0x33, 0x00, 0x6, resto); break;
            case "slt": emitirR(0x33, 0x00, 0x2, resto); break;
            case "mul": emitirR(0x33, 0x01, 0x0, resto); break;
            case "div": emitirR(0x33, 0x01, 0x4, resto); break;
            case "addi": emitirI(0x13, 0x0, resto); break;
            case "slti": emitirI(0x13, 0x2, resto); break;
            case "sltiu": emitirI(0x13, 0x3, resto); break;
            case "seqz": emitirSeqz(resto); break;
            case "andi": emitirI(0x13, 0x7, resto); break;
            case "xori": emitirI(0x13, 0x4, resto); break;
            case "lw": emitirLoad(0x03, 0x2, resto); break;
            case "lb": emitirLoad(0x03, 0x0, resto); break;
            case "sw": emitirStore(0x23, 0x2, resto); break;
            case "sb": emitirStore(0x23, 0x0, resto); break;
            case "jalr": emitirJalr(resto); break;
            case "jal": emitirJal(resto, REG_RA); break;
            case "j": emitirJal(resto, REG_ZERO); break;
            case "ret": emitirPalabra(encJalr(REG_ZERO, REG_RA, 0)); break;
            case "bnez": emitirBnez(resto); break;
            case "mv": emitirMv(resto); break;
            case "li": emitirLi(resto); break;
            case "la": emitirLa(resto); break;
            case "ecall": emitirPalabra(0x00000073); break;
            case "fmv.w.x": emitirF(0x53, 0x00, 0x0, resto, true); break;
            case "fmv.x.w": emitirF(0x53, 0x00, 0x0, resto, false); break;
            case "fadd.s": emitirF(0x53, 0x00, 0x0, resto, false); break;
            case "fsub.s": emitirF(0x53, 0x04, 0x0, resto, false); break;
            case "fmul.s": emitirF(0x53, 0x08, 0x0, resto, false); break;
            case "fdiv.s": emitirF(0x53, 0x0c, 0x0, resto, false); break;
            case "frem.s": emitirF(0x53, 0x10, 0x0, resto, false); break;
            case "feq.s": emitirFCmp(0x10, resto); break;
            case "flt.s": emitirFCmp(0x18, resto); break;
            case "fle.s": emitirFCmp(0x1a, resto); break;
            case "fcvt.s.w": emitirF(0x53, 0x10, 0x0, resto, true); break;
            case "fcvt.w.s": emitirF(0x53, 0x18, 0x0, resto, false); break;
            default:
                // Instrucción no soportada: emitir nop.
                emitirPalabra(0x00000013); // addi zero, zero, 0
                break;
        }
    }

    // --- helpers de codificación ---

    private String obtenerMnemotecnico(String s) {
        int sp = s.indexOf(' ');
        if (sp < 0) {
            return s;
        }
        return s.substring(0, sp);
    }

    private String obtenerResto(String s) {
        int sp = s.indexOf(' ');
        if (sp < 0) {
            return "";
        }
        return s.substring(sp + 1).trim();
    }

    private void emitirPalabra(int palabra) {
        agregarPalabra(palabra);
    }

    private int registro(String nombre) {
        Integer r = registros.get(nombre.trim());
        return (r != null) ? r : 0;
    }

    private int parseInmediato(String s) {
        s = s.trim();
        try {
            if (s.startsWith("0x") || s.startsWith("0X")) {
                return Integer.parseInt(s.substring(2), 16);
            }
            return Integer.parseInt(s);
        } catch (Exception ex) {
            return 0;
        }
    }

    private int imm12(int valor) {
        return valor & 0xFFF;
    }

    private int encR(int opcode, int funct7, int funct3, int rd, int rs1, int rs2) {
        return (funct7 << 25) | (rs2 << 20) | (rs1 << 15) | (funct3 << 12)
                | (rd << 7) | opcode;
    }

    private int encI(int opcode, int funct3, int rd, int rs1, int imm) {
        return (imm12(imm) << 20) | (rs1 << 15) | (funct3 << 12)
                | (rd << 7) | opcode;
    }

    private int encS(int opcode, int funct3, int rs2, int rs1, int imm) {
        int imm5 = imm & 0x1F;
        int immHi = (imm >> 5) & 0x7F;
        return (immHi << 25) | (rs2 << 20) | (rs1 << 15) | (funct3 << 12)
                | (imm5 << 7) | opcode;
    }

    private int encSB(int opcode, int funct3, int rs2, int rs1, int imm) {
        // imm es desplazamiento en bytes (maltiplo de 2)
        int imm12 = ((imm >> 12) & 1) << 31;
        int imm10_5 = ((imm >> 5) & 0x3F) << 25;
        int imm4_1 = ((imm >> 1) & 0xF) << 8;
        int imm11 = ((imm >> 11) & 1) << 7;
        return imm12 | imm10_5 | (rs2 << 20) | (rs1 << 15) | (funct3 << 12)
                | imm4_1 | imm11 | opcode;
    }

    private int encUJ(int opcode, int rd, int imm) {
        // imm es desplazamiento en bytes (maltiplo de 2), 20 bits
        int imm20 = ((imm >> 20) & 1) << 31;
        int imm10_1 = ((imm >> 1) & 0x3FF) << 21;
        int imm11 = ((imm >> 11) & 1) << 20;
        int imm19_12 = ((imm >> 12) & 0xFF) << 12;
        return imm20 | imm10_1 | imm11 | imm19_12 | (rd << 7) | opcode;
    }

    private void emitirR(int opcode, int funct7, int funct3, String resto) {
        String[] op = dividirOperandos(resto, 3);
        int rd = registro(op[0]);
        int rs1 = registro(op[1]);
        int rs2 = registro(op[2]);
        emitirPalabra(encR(opcode, funct7, funct3, rd, rs1, rs2));
    }

    private void emitirI(int opcode, int funct3, String resto) {
        String[] op = dividirOperandos(resto, 3);
        int rd = registro(op[0]);
        int rs1 = registro(op[1]);
        int imm = parseInmediato(op[2]);
        emitirPalabra(encI(opcode, funct3, rd, rs1, imm));
    }

    private void emitirLoad(int opcode, int funct3, String resto) {
        // formato: rd, imm(rs1)
        String[] op = dividirOperandos(resto, 2);
        int rd = registro(op[0]);
        String mem = op[1];
        int[] m = parseMemoria(mem);
        int rs1 = m[0];
        int imm = m[1];
        emitirPalabra(encI(opcode, funct3, rd, rs1, imm));
    }

    private void emitirStore(int opcode, int funct3, String resto) {
        // formato: rs2, imm(rs1)
        String[] op = dividirOperandos(resto, 2);
        int rs2 = registro(op[0]);
        String mem = op[1];
        int[] m = parseMemoria(mem);
        int rs1 = m[0];
        int imm = m[1];
        emitirPalabra(encS(opcode, funct3, rs2, rs1, imm));
    }

    private int[] parseMemoria(String mem) {
        // "offset(registro)"
        int par = mem.indexOf('(');
        int imm = 0;
        String reg = mem;
        if (par >= 0) {
            imm = parseInmediato(mem.substring(0, par).trim());
            reg = mem.substring(par + 1, mem.indexOf(')')).trim();
        }
        return new int[]{registro(reg), imm};
    }

    private void emitirJalr(String resto) {
        // "rd, rs1, imm" o solo "rs1" (indirecto)
        String[] op = dividirOperandos(resto, 3);
        int rd = (op.length > 0 && !op[0].isEmpty()) ? registro(op[0]) : REG_ZERO;
        int rs1 = (op.length > 1 && !op[1].isEmpty()) ? registro(op[1]) : REG_ZERO;
        int imm = (op.length > 2 && !op[2].isEmpty()) ? parseInmediato(op[2]) : 0;
        emitirPalabra(encI(0x67, 0x0, rd, rs1, imm));
    }

    private void emitirSeqz(String resto) {
        // "rd, rs" -> sltiu rd, rs, 1  (rd=1 si rs==0)
        String[] op = dividirOperandos(resto, 2);
        int rd = registro(op[0]);
        int rs = registro(op[1]);
        emitirPalabra(encI(0x13, 0x3, rd, rs, 1));
    }

    private void emitirJal(String resto, int rd) {
        String destino = resto.trim();
        int dir = resolverDireccion(destino);
        int pcActual = direccionDe(codigo.size());
        int imm = dir - pcActual;
        emitirPalabra(encUJ(0x6F, rd, imm));
    }

    private void emitirBnez(String resto) {
        // "rs1, etiqueta"
        String[] op = dividirOperandos(resto, 2);
        int rs1 = registro(op[0]);
        int dir = resolverDireccion(op[1].trim());
        int pcActual = direccionDe(codigo.size());
        int imm = dir - pcActual;
        emitirPalabra(encSB(0x63, 0x1, REG_ZERO, rs1, imm));
    }

    private void emitirMv(String resto) {
        // "rd, rs"
        String[] op = dividirOperandos(resto, 2);
        int rd = registro(op[0]);
        int rs = registro(op[1]);
        emitirPalabra(encI(0x13, 0x0, rd, rs, 0));
    }

    private void emitirLi(String resto) {
        // "rd, imm"
        String[] op = dividirOperandos(resto, 2);
        int rd = registro(op[0]);
        int imm = parseInmediato(op[1]);
        if (imm >= -2048 && imm <= 2047) {
            emitirPalabra(encI(0x13, 0x0, rd, REG_ZERO, imm));
        } else {
            int hi = imm >> 12;
            int lo = imm & 0xFFF;
            // corregir signo si lo es negativo tras lui
            int corr = (lo >= 2048) ? 1 : 0;
            emitirPalabra(encI(0x37, 0x0, rd, REG_ZERO, (hi + corr) << 0));
            emitirPalabra(encI(0x13, 0x0, rd, rd, lo - (corr << 12)));
        }
    }

    private void emitirLa(String resto) {
        // "rd, etiqueta"
        String[] op = dividirOperandos(resto, 2);
        int rd = registro(op[0]);
        int dir = resolverDireccion(op[1].trim());
        emitirLi(rd + ", " + dir);
    }

    private int encJalr(int rd, int rs1, int imm) {
        return encI(0x67, 0x0, rd, rs1, imm);
    }

    private void emitirF(int opcode, int funct7, int funct3, String resto, boolean srcEsInt) {
        String[] op = dividirOperandos(resto, 3);
        int rd = registro(op[0]);
        int rs2 = (op.length > 1 && !op[1].isEmpty()) ? registro(op[1]) : 0;
        int rs1 = srcEsInt ? REG_ZERO : ((op.length > 2 && !op[2].isEmpty()) ? registro(op[2]) : 0);
        // Para fmv.w.x/fcvt.s.w: rs2=int origen, rd=float destino, rs1=0.
        // Para fmv.x.w/fcvt.w.s: rs2=float origen, rd=int destino, rs1=0.
        // Para fadd/fsub/fmul/fdiv/frem: rs1, rs2 float, rd float.
        emitirPalabra(encR(opcode, funct7, funct3, rd, rs1, rs2));
    }

    private void emitirFCmp(int funct7, String resto) {
        // "rd, fs1, fs2" -> rd int, fs1/fs2 float
        String[] op = dividirOperandos(resto, 3);
        int rd = registro(op[0]);
        int rs1 = registro(op[1]);
        int rs2 = registro(op[2]);
        emitirPalabra(encR(0x53, funct7, 0x2, rd, rs1, rs2));
    }

    private String[] dividirOperandos(String resto, int max) {
        String[] partes = resto.split(",");
        List<String> res = new ArrayList<>();
        for (String p : partes) {
            res.add(p.trim());
        }
        while (res.size() < max) {
            res.add("");
        }
        return res.subList(0, Math.min(res.size(), max))
                .toArray(new String[0]);
    }

    // ------------------------------------------------------------------
    // ELF32
    // ------------------------------------------------------------------

    private byte[] construirElf() {
        int textoTam = codigo.size();
        // Alinear tama\u00f1o del segmento a 4.
        while ((textoTam % 4) != 0) {
            codigo.add((byte) 0);
            textoTam++;
        }

        int entrada = getEntryPoint();
        int phoff = 52;
        int phentsize = 32;
        int phnum = 1;
        int ehsize = 52;

        int total = ehsize + (phentsize * phnum) + textoTam;

        List<Byte> elf = new ArrayList<>();

        // e_ident[16]
        add(elf, (byte) 0x7F);
        add(elf, (byte) 'E');
        add(elf, (byte) 'L');
        add(elf, (byte) 'F');
        add(elf, (byte) 1);   // class 32
        add(elf, (byte) 1);   // data LE
        add(elf, (byte) 1);   // version
        add(elf, (byte) 0);   // osabi
        add(elf, (byte) 0);   // abiversion
        for (int i = 0; i < 7; i++) {
            add(elf, (byte) 0);
        }
        // e_type = ET_EXEC (2)
        addU16LE(elf, 2);
        // e_machine = EM_RISCV (243)
        addU16LE(elf, 243);
        // e_version = 1
        addU32LE(elf, 1);
        // e_entry
        addU32LE(elf, entrada);
        // e_phoff
        addU32LE(elf, phoff);
        // e_shoff = 0
        addU32LE(elf, 0);
        // e_flags = 0
        addU32LE(elf, 0);
        // e_ehsize
        addU16LE(elf, ehsize);
        // e_phentsize
        addU16LE(elf, phentsize);
        // e_phnum
        addU16LE(elf, phnum);
        // e_shentsize, e_shnum, e_shstrndx = 0
        addU16LE(elf, 0);
        addU16LE(elf, 0);
        addU16LE(elf, 0);

        // Program header (PT_LOAD)
        // p_type = 1
        addU32LE(elf, 1);
        // p_offset = TAMAÑO_HEADERS (saltea ELF header + program header)
        addU32LE(elf, TAMAÑO_HEADERS);
        // p_vaddr = BASE_MEMORIA
        addU32LE(elf, BASE_MEMORIA);
        // p_paddr = mismo
        addU32LE(elf, BASE_MEMORIA);
        // p_filesz = textoTam (datos + texto)
        addU32LE(elf, textoTam);
        // p_memsz = textoTam
        addU32LE(elf, textoTam);
        // p_flags = R|X = 5
        addU32LE(elf, 5);
        // p_align = 4
        addU32LE(elf, 4);

        // .text
        for (Byte b : codigo) {
            add(elf, b);
        }

        byte[] arr = new byte[elf.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = elf.get(i);
        }
        return arr;
    }

    private void add(List<Byte> elf, byte b) {
        elf.add(b);
    }

    private void addU16LE(List<Byte> elf, int valor) {
        elf.add((byte) (valor & 0xFF));
        elf.add((byte) ((valor >> 8) & 0xFF));
    }

    private void addU32LE(List<Byte> elf, int valor) {
        elf.add((byte) (valor & 0xFF));
        elf.add((byte) ((valor >> 8) & 0xFF));
        elf.add((byte) ((valor >> 16) & 0xFF));
        elf.add((byte) ((valor >> 24) & 0xFF));
    }

    // ------------------------------------------------------------------
    // Utilidades
    // ------------------------------------------------------------------

    private List<String> dividirLineas(String texto) {
        List<String> res = new ArrayList<>();
        for (String l : texto.split("\n")) {
            res.add(l);
        }
        return res;
    }
}
