/**
 * Created by Phuc-Hau Nguyen on 1/4/2017.
 */

export class Marker {

  constructor(public name: string,
              public lat: number,
              public lng: number,
              public draggable: boolean) {
  }
}

export class MarkerStorage {

  private galaxyMarkers: Marker[] = [
    {
      name: "GALAXY NGUYỄN DU",
      lat: 10.7732941,
      lng: 106.690802,
      draggable: false
    },
    {
      name: "GALAXY TÂN BÌNH",
      lat: 10.7902587,
      lng: 106.6385232,
      draggable: false
    },
    {
      name: "GALAXY KINH DƯƠNG VƯƠNG",
      lat: 10.7500903,
      lng: 106.6259716,
      draggable: false
    },
    {
      name: "GALAXY QUANG TRUNG",
      lat: 10.8347421,
      lng: 106.6600354,
      draggable: false
    }
  ];
  private cgvMarkers: Marker[] = [
    {
      name: "CGV Hùng Vương Plaza",
      lat: 10.7557866,
      lng: 106.6608583,
      draggable: false
    },
    {
      name: "CGV Crescent Mall",
      lat: 10.72907,
      lng: 106.7167563,
      draggable: false
    },
    {
      name: "CGV CT Plaza",
      lat: 10.813248,
      lng: 106.6636266,
      draggable: false
    },
    {
      name: "CGV Paragon",
      lat: 10.729619,
      lng: 106.7194853,
      draggable: false
    },
    {
      name: "CGV Pandora City",
      lat: 10.8069193,
      lng: 106.6320505,
      draggable: false
    },
    {
      name: "CGV Celadon Tân Phú",
      lat: 10.801702,
      lng: 106.6157859,
      draggable: false
    },
    {
      name: "CGV Thảo Điền Pearl",
      lat: 10.8017757,
      lng: 106.730614,
      draggable: false
    },
    {
      name: "CGV Liberty Citypoint ",
      lat: 10.7745725,
      lng: 106.6984946,
      draggable: false
    },
    {
      name: "CGV Thủ Đức",
      lat: 10.850432,
      lng: 106.7631052,
      draggable: false
    },
    {
      name: "CGV SC VivoCity",
      lat: 10.7301828,
      lng: 106.7016488,
      draggable: false
    },
    {
      name: "CGV Pearl Plaza",
      lat: 10.8000849,
      lng: 106.7164107,
      draggable: false
    },
    {
      name: "CGV Gò Vấp",
      lat: 10.8270443,
      lng: 106.6870531,
      draggable: false
    },
    {
      name: "CGV Hoàng Văn Thụ ",
      lat: 10.7988799,
      lng: 106.657435,
      draggable: false
    },
    {
      name: "CGV Aeon Bình Tân",
      lat: 10.743435,
      lng: 106.6101133,
      draggable: false
    },
    {
      name: "CGV Vincom Đồng Khởi",
      lat: 10.7779234,
      lng: 106.6997641,
      draggable: false
    },
    {
      name: "CGV Saigonres Nguyễn Xí",
      lat: 10.816261,
      lng: 106.7049053,
      draggable: false
    }
  ];
  private bhdMarkers: Marker[] = [
    {
      name: "BHD Star VINCOM LÊ VĂN VIỆT",
      lat: 10.8456724,
      lng: 106.7769402,
      draggable: false
    },
    {
      name: "BHD Star VINCOM THẢO ĐIỀN",
      lat: 10.8023027,
      lng: 106.7387652,
      draggable: false
    },
    {
      name: "BHD Star VINCOM QUANG TRUNG",
      lat: 10.8292332,
      lng: 106.6703046,
      draggable: false
    },
    {
      name: "BHD Star VINCOM 3/2",
      lat: 10.8292312,
      lng: 106.6374737,
      draggable: false
    },
    {
      name: "BHD Star Cineplex Satra Pham Hung",
      lat: 10.733311,
      lng: 106.6717529,
      draggable: false
    },
    {
      name: "BHD Star Cineplex ICON 68 Bitexco's group",
      lat: 10.7716687,
      lng: 106.7021967,
      draggable: false
    }
  ];
  private lotteMarkers: Marker[] = [
    {
      name: "Lotte Cantavil",
      lat: 10.8017625,
      lng: 106.7446821,
      draggable: false
    },
    {
      name: "Lotte Cinema Cộng Hoà",
      lat: 10.801236,
      lng: 106.6505638,
      draggable: false
    },
    {
      name: "Lotteria Diamond",
      lat: 10.7813623,
      lng: 106.6964442,
      draggable: false
    }, {
      name: "Lotte Cinema Go Vap",
      lat: 10.8383785,
      lng: 106.6687854,
      draggable: false
    }, {
      name: "Lotte Cinema Nam Sai Gon",
      lat: 10.7409423,
      lng: 106.700126,
      draggable: false
    }, {
      name: "Lotte Cinema Nowzone",
      lat: 10.764032,
      lng: 106.6810413,
      draggable: false
    }, {
      name: "Lotte Cinema Phú Thọ",
      lat: 10.7632651,
      lng: 106.6545911,
      draggable: false
    }, {
      name: "Lotte Cinema Thủ Đức",
      lat: 10.8712548,
      lng: 106.7670491,
      draggable: false
    }
  ];

  public load() {
    if (localStorage.getItem('markers_galaxy') === null ||
      localStorage.getItem('markers_galaxy') === undefined) {
      localStorage.setItem('markers_galaxy', JSON.stringify(this.galaxyMarkers));
    }
    if (localStorage.getItem('markers_cgv') === null ||
      localStorage.getItem('markers_cgv') === undefined) {
      localStorage.setItem('markers_cgv', JSON.stringify(this.cgvMarkers));
    }
    if (localStorage.getItem('markers_bhd') === null ||
      localStorage.getItem('markers_bhd') === undefined) {
      localStorage.setItem('markers_bhd', JSON.stringify(this.bhdMarkers));
    }
    if (localStorage.getItem('markers_lotte') === null ||
      localStorage.getItem('markers_lotte') === undefined) {
      localStorage.setItem('markers_lotte', JSON.stringify(this.lotteMarkers));
    }
  }
}
