"use strict";

// RequireJS 설정 객체
var require = {
    baseUrl: '/static/js',

    paths: {
        // major library
        'jquery': 'lib/jquery-1.11.2.min',
        'underscore': 'lib/underscore-min',
        'TweenMax': 'lib/TweenMax.min',
        'gsap': 'lib/jquery.gsap.min',
        'scrollto': 'lib/ScrollToPlugin.min', // GSAP ScrollTo plugin
        'bezier': 'ui/BezierEase',  // GSAP Bezier easing

        // library
        'ua-parser-js': 'lib/ua-parser.pack',
        'fastclick': 'lib/fastclick',
        'masonry': 'lib/masonry.pkgd.min',
        'imagesloaded': 'lib/imagesloaded.pkgd.min',
        'skrollr': 'lib/skrollr.min',
        'googlemaps': 'http://maps.google.co.kr/maps/api/js?v=3&region=KR&ver=4.1.1&language=kr&key=AIzaSyDYoYqmDtizBabV5nZ-27yyGaJURnFicWM',

        // jQuery Plug-in
        'wheel': 'lib/jquery.mousewheel.min',
        'menuaim': 'lib/jquery.menu-aim',
        'tmpl': 'lib/jquery.tmpl.min',
        'slick': 'lib/slick',
        'modal': 'lib/jquery.modal.min',
        'sticky': 'lib/jquery.sticky-kit.min',

        // custom
        'smoothscroll': 'ui/smoothscroll',
        'scroller': 'ui/jquery.scroller',
        'ytiframe': 'ui/jquery.ytiframe',
        'drawborder': 'ui/jquery.drawborder',
        'inview': 'ui/jquery.inview',
        'simpletab': 'ui/jquery.simpletab',
        'spinner': 'ui/jquery.spinner',
        'lightbox': 'ui/jquery.lightbox',
        'rating': 'ui/jquery.rating'
    },

    shim: {
        'gsap': {
            deps: ['jquery', 'TweenMax']
        },
        'scrollto': {
            deps: ['TweenMax']
        },
        'bezier': {
            deps: ['TweenMax']
        },
        'menuaim': {
            deps: ['jquery']
        },
        'tmpl': {
            deps: ['jquery']
        },
        'scroller': {
            deps: ['jquery', 'gsap', 'wheel']
        },
        'modal': {
            deps: ['jquery']
        },
        'sticky': {
            deps: ['jquery']
        },
        'masonry': {
            deps: ['jquery', 'imagesloaded']
        },
        'imagesloaded': {
            deps: ['jquery']
        },
        'ytiframe': {
            deps: ['jquery', 'lib/swfobject']
        },
        'drawborder': {
            deps: ['jquery', 'gsap']
        },
        'simpletab': {
            deps: ['jquery']
        },
        'inview': {
            deps: ['jquery']
        },
        'googlemaps': {
            exports: 'google'
        },
        'spinner': {
            deps: ['jquery']
        },
        'lightbox': {
            deps: ['jquery']
        },
        'rating': {
            deps: ['jquery']
        }
    },

    extended: {}
};