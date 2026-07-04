// =====================================================
// DataModels.kt
// Tugas Kelompok PBL Sprint - M5
// Sistem Donasi Mahasiswa
// Implementasi Enkapsulasi: private set dan custom setter
// =====================================================

class Donatur(
    val nama: String,
    saldoAwal: Int
) {
    var saldo: Int = saldoAwal
        private set

    init {
        require(nama.isNotBlank()) { "Nama donatur tidak boleh kosong" }
        require(saldoAwal >= 0) { "Saldo awal tidak boleh negatif" }
    }

    fun tambahSaldo(nominal: Int): Boolean {
        if (nominal <= 0) {
            println("Ditolak: nominal top up harus lebih dari 0")
            return false
        }

        saldo += nominal
        println("Top up berhasil. Saldo $nama sekarang Rp$saldo")
        return true
    }

    fun donasi(nominal: Int, kampanye: Kampanye): Boolean {
        if (nominal <= 0) {
            println("Ditolak: nominal donasi harus lebih dari 0")
            return false
        }

        if (nominal > saldo) {
            println("Ditolak: saldo $nama tidak cukup")
            return false
        }

        if (!kampanye.aktif) {
            println("Ditolak: kampanye sudah ditutup")
            return false
        }

        saldo -= nominal
        kampanye.tambahDonasi(nominal)
        println("Donasi $nama sebesar Rp$nominal berhasil")
        return true
    }
}

class Kampanye(
    judulAwal: String,
    targetAwal: Int,
    val pembuat: String
) {
    var judul: String = judulAwal
        set(value) {
            if (value.isBlank()) {
                println("Ditolak: judul kampanye tidak boleh kosong")
            } else {
                field = value.trim()
            }
        }

    var target: Int = targetAwal
        set(value) {
            if (value <= 0) {
                println("Ditolak: target donasi harus lebih dari 0")
            } else {
                field = value
            }
        }

    var terkumpul: Int = 0
        private set

    var aktif: Boolean = true
        private set

    init {
        require(judulAwal.isNotBlank()) { "Judul kampanye tidak boleh kosong" }
        require(targetAwal > 0) { "Target donasi harus lebih dari 0" }
        require(pembuat.isNotBlank()) { "Nama pembuat tidak boleh kosong" }
    }

    fun tambahDonasi(nominal: Int): Boolean {
        if (!aktif) {
            println("Ditolak: kampanye $judul sudah tidak aktif")
            return false
        }

        if (nominal <= 0) {
            println("Ditolak: nominal donasi harus lebih dari 0")
            return false
        }

        terkumpul += nominal

        if (terkumpul >= target) {
            aktif = false
            println("Target kampanye $judul tercapai. Kampanye ditutup.")
        }

        return true
    }

    fun tampilkanInfo() {
        val status = if (aktif) "Aktif" else "Ditutup"
        println("Kampanye: $judul")
        println("Pembuat  : $pembuat")
        println("Target   : Rp$target")
        println("Terkumpul: Rp$terkumpul")
        println("Status   : $status")
    }
}

fun main() {
    println("=== SIMULASI DATA MODEL ENKAPSULASI ===")

    val kampanye = Kampanye(
        judulAwal = "Donasi Bencana Alam Sumatera",
        targetAwal = 500000,
        pembuat = "Muhammad Aji Kurniawan"
    )

    val donatur = Donatur(
        nama = "Rafi Muhammad Akbar",
        saldoAwal = 300000
    )

    kampanye.tampilkanInfo()
    println("Saldo awal ${donatur.nama}: Rp${donatur.saldo}")

    println("\n--- Uji 1: nominal donasi tidak valid ---")
    donatur.donasi(0, kampanye)

    println("\n--- Uji 2: saldo tidak cukup ---")
    donatur.donasi(600000, kampanye)

    println("\n--- Uji 3: donasi berhasil ---")
    donatur.donasi(100000, kampanye)

    println("\n--- Uji 4: custom setter judul dan target ---")
    kampanye.judul = ""
    kampanye.target = -100000
    kampanye.judul = "Donasi Mahasiswa Peduli"
    kampanye.target = 500000

    println("\n--- Kondisi akhir ---")
    kampanye.tampilkanInfo()
    println("Saldo akhir ${donatur.nama}: Rp${donatur.saldo}")

    // Baris di bawah ini sengaja dikomentari karena akan ERROR COMPILER.
    // donatur.saldo = 9999999
    // kampanye.terkumpul = 0
    // kampanye.aktif = false
}
