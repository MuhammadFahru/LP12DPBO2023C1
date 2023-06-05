# Latihan Praktikum 12 DPBO

## Janji

Bismillah Saya Muhammad Fahru Rozi [2108927] mengerjakan soal Latihan Praktikum 12 dalam mata kuliah Desain Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

## Data Diri

- 2108927
- Muhammad Fahru Rozi
- Ilmu Komputer C1'21
- Universitas Pendidikan Indonesia

## Alasan

Alasan saya menggunakan MVVM dikarenakan beberapa hal, yaitu diantaranya:
- Pemisahan antara tampilan dan logika bisnis
  MVVM memisahkan tampilan (View) dari logika bisnis (Model) dengan menggunakan ViewModel sebagai perantara. Hal ini membuat kode menjadi lebih terstruktur dan mudah dipahami. Dalam konteks project game Synchronization ini berarti pemisahan antara kode yang mengatur tampilan game dengan kode yang mengatur logika permainan. Dalam hal ini Game.java berperan sebagai perantara.
- Binding dua arah
  Salah satu keunggulan MVVM adalah adanya two-way data binding, yang memungkinkan perubahan pada View secara otomatis diteruskan ke ViewModel, dan sebaliknya.
- Fleksibel
  Dalam MVVM, antara Front-End sebagai desainer antarmuka dan Back-End sebagai pengembang logika bisnis bisa bekerja secara independen dan juga lebih mudah dalam berkolaborasi dikarenakan adanya ViewModel yang menjadi perantara