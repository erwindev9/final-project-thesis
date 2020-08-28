package com.erwindevtech.wisatareligi.mymaps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.erwindevtech.wisatareligi.R
import com.erwindevtech.wisatareligi.mymaps.models.LocationMap
import com.erwindevtech.wisatareligi.mymaps.models.Place
import kotlinx.android.synthetic.main.activity_map.*

const val EXTRA_USER_MAP = "EXTRA_USER_MAP"
private const val TAG = "MainActivity"

class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        val userMaps = generateSampleData()

        supportActionBar!!.title = "Kabupaten"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //Set Layout Manager
        rvMaps.layoutManager = LinearLayoutManager(this)

        //Set Adapter on the rcv
        rvMaps.adapter = MapsAdapter(this, userMaps,object :MapsAdapter.OnClickListener{

            override fun onItemClick(position: Int) {
//                Log.i(TAG,"onItemClick $position")
                //even click, to navigate new activity
                val intent = Intent (this@MapActivity,DisplayMapsActivity::class.java)
                intent.putExtra(EXTRA_USER_MAP,userMaps[position])
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }

        })
    }
    private fun generateSampleData(): List<LocationMap> {
        return listOf(
            LocationMap(
                "Asahan",
                listOf(
                    Place("GBI Kota Mas Kisaran", "GBI Kota Mas Kisaran adalah gereja yang berlokasi di kecamatan Kota Kisaran Barat", 2.980401, 99.620314),
                    Place("GBKP Runggun Kisaran", "GBKP Runggun Kisaran adalah gereja yang berlokasi di kecamatan Kota Kisaran Barat", 2.984023, 99.624316),
                    Place("Gereja HKBP Kisaran","Gereja HKBP Kisaran adalah gereja yang berlokasi di kecamatan Kota Kisaran Barat",2.980871,99.622688),
                    Place("Gereja Katolik Sakramen Mahakudus Kisaran","Gereja HKBP Kisaran adalah gereja yang berlokasi di kecamatan Kota Kisaran Barat",2.984411,99.625004),
                    Place("Gereja Methodist Indonesia (GMI) Jemaat Maranatha Kisaran","Gereja Methodist Indonesia (GMI) Jemaat Maranatha Kisaran adalah gereja yang berlokasi di kecamatan Kota Kisaran Barat",2.9851,99.625738),
                    Place("Kelenteng Dewi Samudera","Kelenteng Dewi Samudera atau Kelenteng Ma Co Po dibangun dengan arsitektur bergaya China",2.968701,99.807766),
                    Place("Kelenteng Kiu Lie Tong","Kelenteng Kiu Lie Tong",2.981649,99.630997),
                    Place("Klenteng Kisaran","Klenteng Kisaran adalah vihara yang berlokasi di kecamatan Kisaran Timur",2.98395,99.630736),
                    Place("Klenteng Pintu 12","Klenteng Pintu 12 adalah vihara yang berlokasi di kecamatan Kota Kisaran Barat",2.982498,99.625838),
                    Place("Kristus Raja Stasi Sei Piring","Kristus Raja Stasi Sei Piring adalah gereja yang berlokasi di kecamatan Rahuning",2.71706,99.609795),
                    Place("Kuil Shri Murugan","Kuil Shri Murugan adalah pura yang berlokasi di kecamatan Kota Kisaran Barat",2.984536,99.623484),
                    Place("Masjid ABRARUL HAO Haji Kasim","Masjid ABRARUL HAO Haji Kasim adalah masjid yang berlokasi di kecamatan Kisaran Timur",2.98437,99.664054),
                    Place("Masjid Ahmad Bakrie ","Desain bangunan masjid Agung Kisaran berbentuk segi empat simetris dan memiliki atap bertingkat dua. Selain itu, pada atap tersebut juga diletakkan sebuah kubah besar sebagai kubah induknya dengan cincin yang diberi warna strip emas. Disekeliling kubah induk juga ada 4 menara yang menjulang tinggi pada ke 4 sudut masjid yang diberi warna emas, sama dengan warna kubahnya."
                        ,2.988933,99.611051),
                    Place("Masjid Al Huda","Masjid Al Huda adalah masjid yang berlokasi di kecamatan Kota Kisaran Barat",2.981454,99.628727),
                    Place("Masjid Al Istiqomah Bunut","Masjid Al Istiqomah Bunut- Kisaran adalah masjid yang berlokasi di kecamatan Kota Kisaran Barat",3.015105,99.598247),
                    Place("Masjid Al-Hidayah","Masjid Al-Hidayah adalah masjid yang berlokasi di kecamatan Kota Kisaran Barat",2.985487,99.6228),
                    Place("Masjid Al-Husna","Masjid Al-Husna adalah masjid yang berlokasi di kecamatan Kota Kisaran Barat",2.981572,99.617586),
                    Place("Masjid Al-Jihad","Masjid Al-Jihad adalah masjid yang berlokasi di kecamatan Kisaran Timur",2.974456,99.638663),
                    Place("Masjid Amaliyah Sentang","Masjid Amaliyah Sentang adalah masjid yang berlokasi di kecamatan Kisaran Timur",2.955602,99.653246),
                    Place("Masjid Raya Kisaran Kota","Masjid Raya Kisaran Kota adalah masjid yang berlokasi di kecamatan Kota Kisaran Barat",2.978536,99.629847),
                    Place("Masjid Raya Sultan Ahmadsyah","Masjid Raya Sultan Ahmadsyah merupakan salah satu masjid peninggalan monumental Kesultanan Negeri Asahan yang masih ada sampai saat ini. Masjid Ini didirikan oleh Sultan Ahmadsyah dari Asahan, masjid kerajaan sekaligus cagar budaya. Masjid ini mulai dibangun pada tahun 1883. Pada Kompleks Masjid Raya Sultan Asahan Tanjung Balai terdapat kompleks pemakaman keluarga diraja Asahan. Makam yang ditandai beragam bentuk nisan ini dapat menjadi tolak ukur untuk menilai usia masjid atau keberadaan pertapakannya."
                        ,2.97063,99.806086),
                    Place("Masjid SITI ZUBAIDAH","Masjid SITI ZUBAIDAH adalah masjid yang berlokasi di kecamatan Kota Kisaran Barat",2.978883,99.64691),
                    Place("Vihara Paradipa","Vihara Paradipa adalah vihara yang berlokasi di kecamatan Air Jorman",2.998472,99.708708),
                    Place("Vihara Swarna Dwipa Rahuning","Vihara Swarna Dwipa Rahuning adalah vihara yang berlokasi di kecamatan Rahuning",2.718341,99.606708),
                    Place("Vihara Tri Ratna","Desain arsitektur tradisional China, memiliki patung Buddha setinggi 6 meter yang berdiri tegah di lantai 6",2.970301,99.807299)

                )
            ),
            LocationMap("Batu Bara",
                listOf(
                    Place("Gereja HKBP Tanjung Seri", "Gereja HKBP Tanjung Seri adalah gereja yang berlokasi di kecamatan Sei Suka",3.316845 , 99.298722),
                    Place("GEREJA HKBP INDRAPURA KOTA","Gereja HKBP Tanjung Seri adalah gereja yang berlokasi di kecamatan Sei Suka",3.316845,99.368422),
                    Place("Gereja Katolik St.Albertus","Gereja Katolik St.Albertus adalah gereja yang berlokasi di kecamatan Air Putih",3.285527,99.373465),
                    Place("Gereja Oikumene Tanjung Gading","Gereja Oikumene Tanjung Gading adalah gereja yang berlokasi di kecamatan-Sei Suka",3.313535,99.343578),
                    Place("Kubah Datuk","Kubah Datuk Batubara masih diyakini oleh sebahagian masyarakat sebagai tempat keramat yang memiliki keutamaan. ",3.174926,99.482273),
                    Place("Masjid Al Inayah","Masjid Al Inayah adalah masjid yang berlokasi di kecamatan Tanjung Tiram",3.181766,99.635383),
                    Place("Masjid Hasanah","Masjid Hasanah adalah masjid yang berlokasi di kecamatan Limapuluh",3.197222,99.49433),
                    Place("Masjid Hidayatullah","Masjid Hidayatullah adalah masjid yang berlokasi di kecamatan Talawi",3.218968,99.574888),
                    Place("Masjid Jami Simpang Gabus","Masjid Jami Simpang Gabus adalah masjid yang berlokasi di kecamatan Limapuluh",3.250332,99.41351),
                    Place("Masjid Jamik Indrapura","Masjid Jamik Indrapura merupakan tempat beribadah bagi umat beragama islam yang menjadi masjid tertua di Indrapura yang masih menggunakan arsitektur asli seperti pertama dibuat."
                        ,3.282024,99.37101),
                    Place("Masjid Raya Limapuluh","Masjid Raya Limapuluh adalah masjid yang berlokasi di kecamatan Limapuluh",3.168705,99.417473),
                    Place("Mesjid Nurul Huda Tanjung Gading","Mesjid Nurul Huda Tanjung Gading adalah mesjid yang berlokasi di kecamatan Sei Suka",3.312078,99.343578),
                    Place("SHRI BATHTHIRAKALI AMMAN KUIL","SHRI BATHTHIRAKALI AMMAN KUIL adalah pura yang teletak di kecamatan Limapuluh",3.196334,99.456548),
                    Place("Vihara Buddha Siri","Vihara Buddha Siri adalah vihara yang teletak di kecamatan Air Putih",3.271258,99.365679)
                )),
            LocationMap("Dairi",
                listOf(
                    Place("Gereja GMI", "Gereja GMI, Kabupaten Dairi, Sumatera Utara adalah gereja yang berlokasi di kecamatan Sidikalang", 2.74149,98.319423 ),
                    Place("Gereja Katolik St. Dionysius","Gereja Katolik St. Dionysius, Sumbul adalah gereja yang berlokasi di kecamatan Sumbul",2.743654,98.405874),
                    Place("HKBP Resort","HKBP Resort Sidikalang 2 adalah gereja yang berlokasi di kecamatan Sidikalang",2.755195,98.303569),
                    Place("Masjid Agung Sidikalang","Masjid Agung Sidikalang adalah masjid yang berlokasi di kecamatan Sidikalang",2.747032,98.303569),
                    Place("Masjid Al Muhajirin","Masjid Al Muhajirin  adalah masjid yang berlokasi di kecamatan Sidikalang",2.745677,98.328295),
                    Place("Masjid Jami Besar Bintang","Masjid Jami Besar Bintang  adalah masjid yang berlokasi di kecamatan Sidikalang",2.759881,98.31206),
                    Place("Masjid Telaga ZamZam","Masjid Telaga ZamZam adalah masjid yang berlokasi di kecamatan Sidikalang",2.741133,98.323007),
                    Place("St. Mary of Mt. Carmel, Catholic Church","St. Mary of Mt. Carmel, Catholic Church adalah gereja yang berlokasi di kecamatan Tigalingga",2.913446,98.220572),
                    Place("Taman Wisata Iman","Taman Wisata Iman merupakan tempat wisata religius. Taman Wisata Iman bukan hanya mewakili salah satu agama saja yang diakui di Indonesia melainkan semua agama. Mewakili yang dimaksud adalah bahwa dalam Taman Wisata Iman terdapat berbagai bangunan-bangunan yang duanggap bersejarah bagi pemeluk agama masing-masing. Mulai dari tempat peribadatan hingga miniatur bangunan yang dianggap bersejarah dan mengenangkan peristiwa-peristiwa penting bagi pemeluknya."
                        ,2.742811,98.373747),
                    Place("Vihara Saddhavana","Vihara Saddhavana adalah vihara yang berlokasi di kecamatan Sitinjo",2.740748,98.374554),
                    Place("Vihara Siddhi Maitreya","Vihara Siddhi Maitreya adalah vihara yang berlokasi di kecamatan Sidikalang",2.743766,98.31705)
                )
            ),
            LocationMap("Deli Serdang",
                listOf(
                    Place("BNKP Jemaat Marindal", "BNKP Jemaat Marindal adalah gereja yang teletak di kecamatan Patumbak", 3.526066, 98.70462),
                    Place("Gereja HKBP Delitua","Gereja HKBP Delitua adalah gereja yang berlokasi di kecamatan Deli Tuas",3.48714,98.685531),
                    Place("Gereja Katolik","Gereja Katolik adalah gereja yang berlokasi di kecamatan Patumbak",3.537709,98.762553),
                    Place("Gereja Katolik Gembala Yang Baik Lubuk Pakam","Gereja Katolik Gembala Yang Baik Lubuk Pakam adalah gereja yang berlokasi di kecamatan Lubuk Pakam",3.556194,98.882528),
                    Place("Gereja Katolik Pancur Batu","Gereja Katolik Pancur Batu adalah gereja yang berlokasi di kecamatan Pancur Batu",3.505883,98.595061),
                    Place("Gereja Katolik Santo Yosep Stasi Pagar Jati","Gereja Katolik Santo Yosep Stasi Pagar Jati adalah gereja yang berlokasi di kecamatan Pagar Merbau",3.566659,98.909162),
                    Place("Gereja Katolik St. Rambung Baru","Gereja Katolik St. Rambung Baru adalah gereja yang berlokasi di kecamatan Sibolangit",3.386423,98.590557),
                    Place("Gereja Katolik ST.Clara","Gereja Katolik ST.Clara  adalah gereja yang berlokasi di kecamatan Sunggal",3.634982,98.635586),
                    Place("Gereja Katolik Stasi Undian","Gereja Katolik Stasi Undian adalah gereja yang berlokasi di kecamatan Tanjung Morawa",3.496747,98.742862),
                    Place("GMI Jemaat Immanuel Tanjung Morawa","GMI Jemaat Immanuel Tanjung Morawa adalah gereja yang berlokasi di kecamatan Tanjung Morawa",3.521073,98.79303),
                    Place("ISKCON Medan","ISKCON Medan adalah pura yang teletak di kecamatan Labuhan Deli",3.649544,98.650541),
                    Place("Komplek Gereja Katolik Paroki St. Yosep Delitua","Komplek Gereja Katolik Paroki St. Yosep Delitua adalah gereja yang berlokasi di kecamatan Deli Tua",3.499246,98.681546),
                    Place("Maha Vihara Maitreya","Vihara terbesar di Indonesia ini mulai dibangun tahun 1991 dan diresmikan pada 21 Agustus 2008. Tujuan utama pembangunan Maha Vihara Maitreya pastinya adalah sebagai tempat peribadatan umat Buddha di Medan secara khususnya dan Sumatera Utara secara umum.  Wisata Qur'an juga berhasil menghubungakan masyarakat kepada ulama-ulama pentin dari dalam maupun luar negeri."
                        ,3.645262,98.700581),
                    Place("Masjid Ahmad Patumbak","Masjid Ahmad Patumbak adalah masjid yang berlokasi di kecamatan Patumbak",3.506848,98.71126),
                    Place("Masjid Akhlasiyah","Masjid Akhlasiyah adalah masjid yang berlokasi di kecamatan Tanjung Morawa",3.501469,98.777924),
                    Place("Masjid Al Ikhlas","Masjid Al Ikhlas adalah masjid yang berlokasi di kecamatan Patumbak",3.550622,98.69158),
                    Place("Masjid Al-Kamal","Masjid Al-Kamal adalah tempat peribadatan umat beragama islam yang berada pada Al-Kamal Sibolangit Center, dimana menjadikan masjid tersebut tempat wisata Qur'an yang bertujuan menjadikan masyarakat muslim Sumut yang Qur'ani."
                        ,3.285197,98.556746),
                    Place("Masjid Ar Rahman","Masjid Ar Rahman adalah masjid yang berlokasi di kecamatan Namorambe",3.495767,98.653603),
                    Place("Masjid Ar Raudhah","Masjid Ar Raudhah adalah masjid yang berlokasi di kecamatan Percut Sei Tuan ",3.6158,98.75673),
                    Place("Masjid Asy Syakirin","Masjid Asy Syakirin adalah masjid yang berlokasi di kecamatan Deli Tua",3.503116,98.69808),
                    Place("Masjid Lahmuddin Dalimunthe","Masjid Lahmuddin Dalimunthe adalah masjid yang berlokasi di kecamatan Deli Tua",3.519567,98.679155),
                    Place("Masjid Nur Sa'adah","Masjid Nur Sa'adah adalah masjid yang berlokasi di kecamatan Tanjung Morawa",3.534293,98.75038),
                    Place("Masjid Nurul Iman","Masjid Nurul Iman adalah masjid yang berlokasi di kecamatan Tanjung Morawa",3.521009,98.790283),
                    Place("Pancur Sri Thurga Dewi Temple","Pancur Sri Thurga Dewi Temple  adalah pura yang teletak di kecamatan Pancur Batu",3.474548,98.59427),
                    Place("Shri Mariamman Kuil Bekala","Shri Mariamman Kuil Bekala adalah pura yang teletak di kecamatan Pancur Batu",3.503399,98.625406)
                )
            ),
            LocationMap("Gunung Sitoli",
                listOf(
                    Place("Gereja BNKP Jemaat Kota Gunungsitoli", "Gereja BNKP Jemaat Kota Gunungsitoli adalah gereja yang berlokasi di Kota Gunungsitoli", 1.288516, 97.61764),
                    Place("Gereja GNKP Afilaza","",1.286155,97.615217),
                    Place("Gereja GPI Kota Gunungsitoli","",1.284583,97.622924),
                    Place("Gereja HKBP Gunungsitoli","",1.287836,97.61774),
                    Place("Gereja Katolik St. Fransiskus Assisi, Laverna","",1.306255,97.60762),
                    Place("Gereja Katolik St. Maria Bunda Para Bangsa","",1.284583,97.622924),
                    Place("Masjid Agung Kota Gunungsitoli","",1.282289,97.617887),
                    Place("Masjid Jami Ilir","",1.287048,97.621781),
                    Place("Masjid Raya Al-Furqon","",1.290712,97.614615),
                    Place("Taman Doa Bunda Maria","",1.252048,97.643325),
                    Place("Vihara Vimala Dharma","",1.289786,97.618906)
                )
            ),
            LocationMap("Humbang Hasundutan",
                listOf(
                    Place("Aek Sipangolu Bakarra", "Objek wisata Aek Sipangolu merupakan perpaduan antara wisata alam dengan wisata religi, karena bagi masyarakat setempat Aek Sipangolu merupakan sumber air keramat yang dapat menyembuhkan berbagai macam penyakit. Keberadaan Aek Sipangolu ini menurut mitos yang berkembang berasal dari bekas tapak kaki gajah putih yang menjadi tunggangan Sisingamangaraja.", 2.324238,98.846266 )
                )
            ),
            LocationMap("Karo",
                listOf(
                    Place("Gereja GBKP Moderamen", "Gereja GBKP Moderamen adalah gereja yang berlokasi di kecamatan Kabanjahe", 3.10006, 98.489218)
                )
            ),
            LocationMap("Langkat",
                listOf(
                    Place("Asokarama Buddhist Centre (ABC)", "Asokarama Buddhist Centre (ABC adalah asokarama yang berlokasi di kecamatan Stabat", 3.759698, 98.473909)
                )
            ),
            LocationMap("Mandailing Natal",
                listOf(
                    Place("Gereja GKPA", "Gereja GKPA adalah gereja yang berlokasi di kecamatan Panyabungan", 0.853473, 99.575212)
                )
            ),
            LocationMap("Medan",
                listOf(
                    Place("Gereja GBKP Medan Putri", "Gereja GBKP Medan Putri adalah gereja yang berlokasi di kecamatan Medan Barat", 3.612986,98.663141 )
                )
            ),
            LocationMap("Samosir",
                listOf(
                    Place("Gereja HKBP Ambarita Ressort Ambarita", "Gereja HKBP Ambarita Ressort Ambarita adalah gereja yang berlokasi di kecamatan Simanindo", 2.679444, 98.828746)
                )
            ),
            LocationMap("Simalungun",
                listOf(
                    Place("Gereja HKBP Ressort Dolok Marlawan", "Gereja HKBP Ressort Dolok Marlawan adalah gereja yang berlokasi di kecamatan Jorlang Hataran", 2.848018, 99.038794)
                )
            ),
            LocationMap("Tapanuli Tengah",
                listOf(
                    Place("Gereja HKBP Pandan", "Gereja HKBP Pandan adalah gereja yang berlokasi di kecamatan Pandan", 1.684059,98.823931 )
                )
            )
            ,
            LocationMap("Tapanuli Utara",
                listOf(
                    Place("Bukit Doa", "Bukit Doa Huta Ginjang dapat diartikan sebagai satu titik puncak akan anugerah Tuhan kepada bangso Batak dan terdiri dari 26 ruangan. Lokasi Bukit Doa tepat di lereng bukit yang menghadap langsung ke Danau Toba sebagai harapan agar Tapanuli Uatara senantiasa diberkati.", 2.321823,98.98158 )
                )
            )
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}