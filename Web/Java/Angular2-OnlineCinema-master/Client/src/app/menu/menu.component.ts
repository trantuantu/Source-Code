import {Component, OnInit} from '@angular/core';
import {NavigationMenu} from "../_interfaces/navigation-menu.interface";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  navMenus: NavigationMenu[] = [
    {
      title: "DANH SÁCH PHIM",
      active: true,
      link: "/film"
    },
    {
      title: "KHUYẾN MÃI",
      active: false,
      link: "/hot"
    },
    {
      title: "BẢN ĐỒ",
      active: false,
      link: "/map"
    },
    {
      title: "CHÚNG TÔI",
      active: false,
      link: "/about"
    },
    {
      title: "LIÊN HỆ",
      active: false,
      link: "/contact"
    }
  ];

  constructor() {
  }

  ngOnInit() {
  }

  clicked(index) {
    for (let i in this.navMenus) {
      this.navMenus[i].active = false;
    }
    this.navMenus[index].active = true;
  }
}
