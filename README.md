# Payment Gateway Refactor

## Deskripsi

Mini project pemrograman berorientasi objek lanjut yang berfokus pada penerapan prinsip **SOLID** dalam desain sistem pembayaran (_payment gateway_). Project ini merupakan tugas kelompok mata kuliah OOP Lanjut.

Sistem yang di-refactor awalnya memiliki kode _legacy_ dengan pelanggaran prinsip SOLID. Setelah refactoring, sistem menjadi lebih modular, mudah dikembangkan, dan memiliki _loose coupling_ antar komponen.

---

## Prinsip SOLID yang Diterapkan

| Prinsip | Penjelasan                                                                                              |
| ------- | ------------------------------------------------------------------------------------------------------- |
| **SRP** | Kelas `PaymentProcessor` dipecah menjadi komponen-komponen kecil dengan tanggung jawab spesifik         |
| **OCP** | Penambahan metode pembayaran baru (misal: GoPay) tidak mengubah kode inti sistem                        |
| **LSP** | Setiap metode pembayaran dapat digunakan secara intercambiable melalui interface umum                   |
| **ISP** | Kontrak interface dipisahkan menjadi `PaymentMethod`, `PaymentRepository`, `PaymentNotifier`            |
| **DIP** | Menggunakan _Dependency Injection_ agar kelas tingkat tinggi tidak bergantung pada kelas tingkat rendah |

---

## Struktur Direktori

```
payment-gateway-refactor/
├── .vscode/
│   └── settings.json           # Konfigurasi VS Code (classpath, output)
├── lib/
│   └── mysql-connector-j-8.0.33.jar  # MySQL JDBC Driver
├── src/
│   ├── Main.java               # Titik masuk aplikasi
│   └── payment/
│       ├── PaymentProcessor.java    # Orkestrator pembayaran
│       ├── interfaces/
│       │   ├── PaymentMethod.java    # Kontrak untuk metode pembayaran
│       │   ├── PaymentRepository.java # Kontrak untuk penyimpanan data
│       │   └── PaymentNotifier.java  # Kontrak untuk notifikasi
│       ├── methods/
│       │   ├── CreditCardPayment.java
│       │   ├── PayPalPayment.java
│       │   ├── BitcoinPayment.java
│       │   └── GoPayPayment.java
│       └── services/
│           ├── DatabaseRepository.java  # Implementasi JDBC ke MySQL
│           └── MockEmailNotifier.java    # Implementasi notifikasi email
```

---

## Anggota Tim

| No  | Nama                    | NIM        |
| --- | ----------------------- | ---------- |
| 1   | Winata Fadillah Mubarak | 3337240091 |
| 2   | Abdur Rachman           | 3337240090 |
| 3   | Androni Cristian        | 3337240100 |
| 4   | Ariiq Al Fauzan         | 3337240081 |

---

## Cara Menjalankan

### Prasyarat

- VS Code dengan **Java Extension Pack**
- JDK 11 atau lebih tinggi
- MySQL Server (lokal)

### Langkah

1. Clone repositori ini ke komputer Anda
2. Buka folder project di VS Code
3. Buat file `.env` atau set environment variable untuk koneksi database:
   ```bash
   export DB_URL="jdbc:mysql://localhost:3306/payment_db"
   export DB_USER="root"
   export DB_PASSWORD="password_anda"
   ```
4. Jalankan `Main.java` dari VS Code (tombol **Run** atau `F5`)

---

## Teknologi yang Digunakan

- **Java** — Bahasa pemrograman
- **JDBC** — Koneksi database MySQL
- **VS Code** — Editor dengan konfigurasi workspace khusus

---

## Desain Pola

- **Strategy Pattern** — Untuk variasi metode pembayaran
- **Dependency Injection** — Untuk memisahkan creation dan usage objek
