// to get current year
function getYear() {
    var currentDate = new Date();
    var currentYear = currentDate.getFullYear();
    var yearNode = document.querySelector("#displayYear");
    if (yearNode) {
        yearNode.innerHTML = currentYear;
    }
}

getYear();


// isotope js
$(window).on('load', function () {
    if (typeof $.fn.isotope !== 'function') {
        return;
    }

    $('.filters_menu li').click(function () {
        $('.filters_menu li').removeClass('active');
        $(this).addClass('active');

        var data = $(this).attr('data-filter');
        $grid.isotope({
            filter: data
        })
    });

    var $grid = $(".grid").isotope({
        itemSelector: ".all",
        percentPosition: false,
        masonry: {
            columnWidth: ".all"
        }
    })
});

// nice select
$(document).ready(function() {
    if (typeof $.fn.niceSelect === 'function') {
        $('select').niceSelect();
    }
  });

/** google_map js **/
function myMap() {
    var mapProp = {
        center: new google.maps.LatLng(40.712775, -74.005973),
        zoom: 18,
    };
    var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
}

// client section owl carousel
if (typeof $.fn.owlCarousel === 'function') {
    $(".client_owl-carousel").owlCarousel({
        loop: true,
        margin: 0,
        dots: false,
        nav: true,
        navText: [],
        autoplay: true,
        autoplayHoverPause: true,
        navText: [
            '<i class="fa fa-angle-left" aria-hidden="true"></i>',
            '<i class="fa fa-angle-right" aria-hidden="true"></i>'
        ],
        responsive: {
            0: {
                items: 1
            },
            768: {
                items: 2
            },
            1000: {
                items: 2
            }
        }
    });
}

// slide menu close button inside drawer
$(document).ready(function () {
    // enforce one unified front menu on all pages
    $('.custom_nav-container .navbar-nav').each(function () {
        var $nav = $(this);
        var path = window.location.pathname || '/';
        var hasArtistDashboard = $nav.find('a[href="/artist"], a[href^="/artist?"], a[href^="/artist/"]').length > 0;

        var menuItems = [
            { label: 'Gallery', href: '/gallery' },
            { label: 'Forum', href: '/forum' },
            { label: 'Events', href: '/evenements' },
            { label: 'Courses', href: '/courses' }
        ];
        if (hasArtistDashboard || path.indexOf('/artist') === 0) {
            menuItems.push({ label: 'Artist Dashboard', href: '/artist' });
        }

        var html = '';
        for (var i = 0; i < menuItems.length; i++) {
            var item = menuItems[i];
            var isActive = path === item.href || (item.href !== '/' && path.indexOf(item.href + '/') === 0);
            html += '<li class=\"nav-item' + (isActive ? ' active' : '') + '\"><a class=\"nav-link\" href=\"' + item.href + '\">' + item.label + '</a></li>';
        }
        $nav.html(html);
    });

    $('.custom_nav-container .navbar-collapse').each(function () {
        var $collapse = $(this);
        var $nav = $collapse.find('.navbar-nav').first();

        if ($nav.length === 0 || $nav.find('.slide-menu-close').length > 0) {
            return;
        }

        var $closeBtn = $('<button type="button" class="slide-menu-close" aria-label="Close menu"><i class="fa fa-bars" aria-hidden="true"></i></button>');
        $nav.prepend($closeBtn);

        $closeBtn.on('click', function () {
            $collapse.collapse('hide');
        });
    });

    function closeOpenMenusExceptTarget(event) {
        $('.custom_nav-container .navbar-collapse.show').each(function () {
            var $openCollapse = $(this);
            var $target = $(event.target);

            var clickedInsideMenu = $target.closest('.custom_nav-container .navbar-nav').length > 0;
            var clickedToggler = $target.closest('.custom_nav-container .navbar-toggler').length > 0;

            if (!clickedInsideMenu && !clickedToggler) {
                $openCollapse.collapse('hide');
            }
        });
    }

    // close slide menu when clicking/tapping outside of it
    $(document).on('mousedown touchstart', function (event) {
        closeOpenMenusExceptTarget(event);
    });

    // close with Escape key
    $(document).on('keydown', function (event) {
        if (event.key === 'Escape') {
            $('.custom_nav-container .navbar-collapse.show').collapse('hide');
        }
    });
});
