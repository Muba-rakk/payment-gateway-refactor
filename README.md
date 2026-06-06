# Payment Gateway Refactor
## Mini Project OOP Lanjut - SOLID Principles

---

## 📌 Deskripsi Project

Project ini merupakan tugas mini project mata kuliah **Pemrograman Berorientasi Objek Lanjut** yang berfokus pada penerapan prinsip **SOLID** dalam desain dan refactoring sistem perangkat lunak.

Sistem yang di-refactor adalah sistem pemrosesan pembayaran (*payment gateway*) yang sebelumnya memiliki kode *legacy* dengan berbagai pelanggaran prinsip SOLID. Setelah dilakukan refactoring, sistem menjadi lebih modular, mudah dikembangkan, dan memiliki *loose coupling* antar komponen.

---

## 🎯 Tujuan

1. **Menerapkan SRP (Single Responsibility Principle)** — Memecah tanggung jawab yang terlalu besar pada kelas `PaymentProcessor` menjadi komponen-komponen kecil yang memiliki tugas spesifik.
2. **Menerapkan OCP (Open/Closed Principle)** — Memungkinkan penambahan metode pembayaran baru (seperti `GoPay`) tanpa mengubah kode inti (`PaymentProcessor`).
3. **Menerapkan LSP (Liskov Substitution Principle)** — Setiap metode pembayaran dapat digunakan secara intercambiable melalui interface umum.
4. **Menerapkan ISP (Interface Segregation Principle)** — Memisahkan kontrak interface yang besar menjadi kontrak-kontrak kecil yang lebih spesifik (`PaymentMethod`, `PaymentRepository`, `PaymentNotifier`).
5. **Menerapkan DIP (Dependency Inversion Principle)** — Menggunakan *Dependency Injection* agar kelas tingkat tinggi tidak bergantung langsung pada kelas tingkat rendah.

---

## 🏗️ Arsitektur Sistem

Sistem menggunakan pola desain **Strategy Pattern** dan **Dependency Injection** untuk mencapai fleksibilitas dan keterbacaan kode.

```
┌─────────────────────────────────────────────────┐
│                    Main.java                      │
│             (Entry Point / Composition Root)       │
└──────────────────────┬────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────┐
│              PaymentProcessor                    │
│         (Orchestrator / Kasir)                   │
│  - processPayment(PaymentMethod, amount, ...)    │
└───────┬─────────────────┬───────────────────────┘
        │                 │                 │
        ▼                 ▼                 ▼
┌───────────────┐ ┌─────────────────┐ ┌──────────────────┐
│PaymentMethod  │ │PaymentRepository│ │PaymentNotifier   │
│(Interface)    │ │(Interface)      │ │(Interface)       │
└───────┬───────┘ └────────┬────────┘ └────────┬─────────┘
        │                  │                     │
        ▼                  ▼                     ▼
┌─────────────────┐ ┌─────────────────┐ ┌─────────────────┐
│ CreditCard      │ │ Database        │ │ MockEmail       │
│ PayPal          │ │ Repository      │ │ Notifier        │
│ Bitcoin         │ │ (JDBC/MySQL)    │ │                 │
│ GoPay           │ │                 │ │                 │
└─────────────────┘ └─────────────────┘ └─────────────────┘
```

### Struktur Direktori

```
payment-gateway-refactor/
├── .vscode/
│   └── settings.json       # Konfigurasi VS Code
├── lib/
│   └── mysql-connector-j-8.0.33.jar  # JDBC Driver
├── src/
│   ├── Main.java                        # Titik masuk aplikasi
│   └── payment/
│       ├── PaymentProcessor.java         # Kelas orkestrasi
│       ├── interfaces/
│       │   ├── PaymentMethod.java       # Kontrak pembayaran
│       │   ├── PaymentRepository.java   # Kontrak penyimpanan
│       │   └── PaymentNotifier.java     # Kontrak notifikasi
│       ├── methods/
│       │   ├── CreditCardPayment.java
│       │   ├── PayPalPayment.java
│       │   ├── BitcoinPayment.java
│       │   └── GoPayPayment.java
│       └── services/
│           ├── DatabaseRepository.java    # Implementasi JDBC
│           └── MockEmailNotifier.java   # Implementasi notifikasi
└── PRD.md                   # Product Requirements Document
```

---

## 👥 Daftar Anggota Tim

| No | Nama | NIM |
|----|------|-----|
| 1 | Winata Fadillah Mubarak | 3337240091 |
| 2 | Abdur Rachman | 3337240090 |
| 3 | Androni Cristian | 3337240100 |
| 4 | Ariiq Al Fauzan | 3337240081 |

---

## 📋 Fitur yang Diimplementasikan

### Fitur Utama
- **Credit Card Payment** — Pembayaran menggunakan kartu kredit
- **PayPal Payment** — Pembayaran menggunakan akun PayPal
- **Bitcoin Payment** — Pembayaran menggunakan cryptocurrency Bitcoin
- **GoPay Payment** — *(Fitur Baru)* Pembayaran menggunakan e-wallet GoPay

### Fitur Pendukung
- **Database Integration** — Penyimpanan transaksi ke database MySQL menggunakan JDBC
- **Notification System** — Sistem notifikasi untuk setiap transaksi yang berhasil
- **Environment Variables** — Kredensial database tidak di-hardcode, menggunakan `System.getenv()`

---

## 🔧 Cara Menjalankan

### Prasyarat
- VS Code dengan ekstensi **Java Extension Pack**
- **Java Development Kit (JDK)** versi 11 atau lebih tinggi
- **MySQL** (untuk koneksi database lokal)

### Langkah
1. Clone repositori ini
2. Buka folder project di VS Code
3. Atur kredensial database pada environment variable:
   ```bash
   export DB_URL="jdbc:mysql://localhost:3306/payment_db"
   export DB_USER="root"
   export DB_PASSWORD="password_anda"
   ```
4. Jalankan `Main.java` menggunakan tombol **Run** di VS Code atau perintah:
   ```bash
   javac -d bin -cp "lib/*" src/payment/interfaces/*.java src/payment/methods/*.java src/payment/services/*.java src/payment/*.java src/Main.java
   java -cp "bin:lib/*" Main
   ```

---

## 📚 Referensi

- **Materi Kuliah:** Presentasi OOP Lanjut — SOLID Principles
- **Dokumentasi:** `PRD.md` (Product Requirements Document)
- **Instruksi AI Agent:** `AGENTS.md` (Petunjuk untuk asisten AI)

---

> Project ini dibuat sebagai bagian dari tugas mini project mata kuliah Pemrograman Berorientasi Objek Lanjut. Setiap anggota tim berkontribusi dalam pengimplementasian prinsip SOLID dan kolaborasi menggunakan Git/GitHub.