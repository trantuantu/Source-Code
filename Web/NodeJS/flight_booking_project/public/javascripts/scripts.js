var count1 = 0;
var count2 = 0;

function refreshPage() {
    location.reload();
}

function booking_display() {
    count1++;
    count2 = 0;
    document.getElementById("booking_banner").style.display = "inline-block";
    document.getElementById("info_banner").style.display = "none";
    document.getElementById("top").setAttribute('class', 'blur');
    document.getElementById("khuhoi").className += " btn-info";
    if (count1 % 2 == 0) {
        document.getElementById("booking_banner").style.display = "none";
        document.getElementById("top").setAttribute('class', null);
    }
}

function close_form() {
    count1 = 0;
    document.getElementById("booking_banner").style.display = "none";
    document.getElementById("top").setAttribute('class', null);
}
function close_form1() {
    count2 = 0;
    document.getElementById("info_banner").style.display = "none";
    document.getElementById("top").setAttribute('class', null);
}

function info_display(){
    count2++;
    count1 = 0;
    document.getElementById("info_banner").style.display = "inline-block";
    document.getElementById("booking_banner").style.display = "none";
    document.getElementById("top").setAttribute('class', 'blur');
    if (count2 % 2 == 0) {
        document.getElementById("info_banner").style.display = "none";
        document.getElementById("top").setAttribute('class', null);
    }
}

function change_type(a) {
    if (a == 0) {
        document.getElementById("khuhoi").className += " btn-info";
        document.getElementById("motchieu").className -= " btn-info";
        document.getElementById("motchieu").className += " btn_in_form";
        document.getElementById("motchieu").className += " btn btn-default";

        document.getElementById("example2").style.display = "inline-block";
        document.getElementById("arrow").src = "images/arrow2.png";
    } else {
        document.getElementById("khuhoi").className -= " btn-info";
        document.getElementById("khuhoi").className += " btn_in_form";
        document.getElementById("khuhoi").className += " btn btn-default";
        document.getElementById("motchieu").className += " btn-info";
        document.getElementById("example2").required = false;
        document.getElementById("example2").style.display = "none";

        document.getElementById("arrow").src = "images/arrow1.png";
    }

}

function displayname(name) {
    document.getElementById("menu1").text() = name;
}

function chooseDanhXung(id, str) {
    document.getElementById(id).value = str;
}

$(function() {
    // $('[data-toggle="popover"]').popover();

    $('#cvc').on('click', function() {
        if ($('.cvc-preview-container').hasClass('hide')) {
            $('.cvc-preview-container').removeClass('hide');
        } else {
            $('.cvc-preview-container').addClass('hide');
        }
    });

    $('.cvc-preview-container').on('click', function() {
        $(this).addClass('hide');
    });
});

window.fbAsyncInit = function() {
    FB.init({
        appId: '242781872812410',
        xfbml: true,
        version: 'v2.8',
        status: true
    });
    // FB.AppEvents.logPageView();

    FB.getLoginStatus(function(response) {
        if (response.status === 'connected') {
            
        } else if (response.status === 'not_authorized') {

        } else {
            
        }
    })
};

(function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) {
        return; }
    js = d.createElement(s);
    js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
