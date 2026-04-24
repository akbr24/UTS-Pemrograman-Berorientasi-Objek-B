// =====================================================
// SISTEM DONASI (KOTLIN VERSION)
// =====================================================

data class Donatur(var nama: String, var saldo: Int) {
    fun donasi(nominal: Int): Boolean {
        if (nominal <= 0) {
            println("❌ Nominal tidak valid")
            return false
        }

        if (nominal > saldo) {
            println("❌ Saldo tidak cukup")
            return false
        }

        saldo -= nominal
        return true
    }
}

data class Kampanye(
    val judul: String,
    val target: Int,
    val pembuat: String,
    var terkumpul: Int = 0,
    var aktif: Boolean = true
) {
    fun tambahDonasi(nominal: Int) {
        terkumpul += nominal

        if (terkumpul >= target) {
            aktif = false
            println("🎉 Target tercapai! Kampanye ditutup.")
        }
    }
}

// =====================================================
// DATA GLOBAL
// =====================================================
val daftarKampanye = mutableListOf<Kampanye>()
val daftarDonatur = mutableListOf<Donatur>()


// =====================================================
// MENU PEMBUKA DONASI
// =====================================================
fun menuPembuka() {
    println("\n=== PEMBUKA DONASI ===")

    print("Nama pembuat kampanye: ")
    val nama = readLine()!!

    print("Judul kampanye: ")
    val judul = readLine()!!

    print("Target donasi: ")
    val target = readLine()!!.toInt()

    val kampanye = Kampanye(judul, target, nama)
    daftarKampanye.add(kampanye)

    println("✅ Kampanye berhasil dibuat!")
}


// =====================================================
// MENU DONATUR
// =====================================================
fun menuDonatur() {
    println("\n=== LOGIN DONATUR ===")

    print("Nama: ")
    val nama = readLine()!!

    print("Saldo awal: ")
    val saldo = readLine()!!.toInt()

    val donatur = Donatur(nama, saldo)
    daftarDonatur.add(donatur)

    while (true) {
        println("\n--- MENU DONATUR ---")
        println("Saldo Anda: Rp ${donatur.saldo}")

        val kampanyeAktif = daftarKampanye.filter { it.aktif }

        if (kampanyeAktif.isEmpty()) {
            println("❌ Belum ada kampanye tersedia")
            return
        }

        kampanyeAktif.forEachIndexed { index, k ->
            println("[${index + 1}] ${k.judul} | Target: Rp ${k.target} | Terkumpul: Rp ${k.terkumpul}")
        }

        print("Pilih kampanye: ")
        val pilih = readLine()!!.toIntOrNull()

        if (pilih == null || pilih !in 1..kampanyeAktif.size) {
            println("❌ Pilihan salah")
            continue
        }

        val kampanye = kampanyeAktif[pilih - 1]

        print("Masukkan jumlah donasi: ")
        val nominal = readLine()!!.toIntOrNull()

        if (nominal == null) {
            println("❌ Input harus angka")
            continue
        }

        if (donatur.donasi(nominal)) {
            kampanye.tambahDonasi(nominal)
            println("✅ Donasi berhasil")
        }

        print("Donasi lagi? (y/n): ")
        val lanjut = readLine()!!

        if (lanjut.lowercase() != "y") break
    }
}


// =====================================================
// MENU UTAMA
// =====================================================
fun main() {
    while (true) {
        println("\n=== SISTEM DONASI ===")
        println("1. Buka Donasi (Pembuat Kampanye)")
        println("2. Donatur")
        println("3. Keluar")

        print("Pilih menu: ")
        val pilih = readLine()

        when (pilih) {
            "1" -> menuPembuka()
            "2" -> menuDonatur()
            "3" -> {
                println("Terima kasih!")
                return
            }
            else -> println("❌ Pilihan tidak valid")
        }
    }
}