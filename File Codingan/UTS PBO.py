# =====================================================
# SISTEM DONASI BERBASIS LOGIN
# =====================================================

class Donatur:
    def __init__(self, nama, saldo):
        self.nama = nama
        self.saldo = saldo

    def donasi(self, nominal):
        if nominal <= 0:
            print("❌ Nominal tidak valid")
            return False

        if nominal > self.saldo:
            print("❌ Saldo tidak cukup")
            return False

        self.saldo -= nominal
        return True


class Kampanye:
    def __init__(self, judul, target, pembuat):
        self.judul = judul
        self.target = target
        self.terkumpul = 0
        self.pembuat = pembuat
        self.aktif = True

    def tambah_donasi(self, nominal):
        self.terkumpul += nominal

        if self.terkumpul >= self.target:
            self.aktif = False
            print("🎉 Target tercapai! Kampanye ditutup.")


# =====================================================
# DATA GLOBAL
# =====================================================
daftar_kampanye = []
daftar_donatur = []


# =====================================================
# MENU PEMBUKA DONASI
# =====================================================
def menu_pembuka():
    print("\n=== PEMBUKA DONASI ===")

    nama = input("Nama pembuat kampanye: ")
    judul = input("Judul kampanye: ")
    target = int(input("Target donasi: "))

    kampanye = Kampanye(judul, target, nama)
    daftar_kampanye.append(kampanye)

    print("✅ Kampanye berhasil dibuat!")


# =====================================================
# MENU DONATUR
# =====================================================
def menu_donatur():
    print("\n=== LOGIN DONATUR ===")

    nama = input("Nama: ")
    saldo = int(input("Saldo awal: "))

    donatur = Donatur(nama, saldo)
    daftar_donatur.append(donatur)

    while True:
        print("\n--- MENU DONATUR ---")
        print(f"Saldo Anda: Rp {donatur.saldo:,}")

        # tampilkan kampanye aktif
        kampanye_aktif = [k for k in daftar_kampanye if k.aktif]

        if not kampanye_aktif:
            print("❌ Belum ada kampanye tersedia")
            return

        for i, k in enumerate(kampanye_aktif, 1):
            print(f"[{i}] {k.judul} | Target: Rp {k.target:,} | Terkumpul: Rp {k.terkumpul:,}")

        try:
            pilih = int(input("Pilih kampanye: "))
            if not (1 <= pilih <= len(kampanye_aktif)):
                print("❌ Pilihan salah")
                continue
        except:
            print("❌ Input harus angka")
            continue

        kampanye = kampanye_aktif[pilih - 1]

        try:
            nominal = int(input("Masukkan jumlah donasi: "))
        except:
            print("❌ Input harus angka")
            continue

        # proses donasi
        if donatur.donasi(nominal):
            kampanye.tambah_donasi(nominal)
            print("✅ Donasi berhasil")

        lanjut = input("Donasi lagi? (y/n): ")
        if lanjut.lower() != "y":
            break


# =====================================================
# MENU UTAMA (LOGIN ROLE)
# =====================================================
def main():
    while True:
        print("\n=== SISTEM DONASI ===")
        print("1. Buka Donasi (Pembuat Kampanye)")
        print("2. Donatur")
        print("3. Keluar")

        pilih = input("Pilih menu: ")

        if pilih == "1":
            menu_pembuka()

        elif pilih == "2":
            menu_donatur()

        elif pilih == "3":
            print("Terima kasih!")
            break

        else:
            print("❌ Pilihan tidak valid")


# =====================================================
if __name__ == "__main__":
    main()