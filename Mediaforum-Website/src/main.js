import Vue from "vue";

import {library, dom} from "@fortawesome/fontawesome-svg-core";
import {fas} from "@fortawesome/free-solid-svg-icons";
import {far} from "@fortawesome/free-regular-svg-icons";
import {fab} from "@fortawesome/free-brands-svg-icons";
library.add(fas, far, fab);
dom.watch();

import "./assets/js/bootstrap.js";
import axios from "axios";
import Cookies from "js-cookie";

import "./assets/css/bootstrap.min.css";
import "./assets/css/style.css";


// User Control
function setuplogin(uid, accessToken) {
    Cookies.set("uid", uid, {expires: 10});
    Cookies.set("accessToken", accessToken, {expires: 10});
}
var user = {
    uid: parseInt(Cookies.get("uid")),
    accessToken: Cookies.get("accessToken"),

    email: "",
    username: "",
    avatar_url: "",
    savelists: {
        HISTORY: 0,
        READLATER: 0,
        LIKEDPOSTS: 0,
        created: []
    }
};
function updateuserinfo() {
    if (isFinite(user.uid)) {  // refresh user_info
        request("/api/user_info", {}, resp => {
            user.username = resp.username;
            user.avatar_url = resp.avatar_url;
            user.email = resp.email;
            user.savelists = resp.savelists;
        });
    }
}
updateuserinfo();

// API Networking Requestion
function request(path, data, respf, errf, lcall = ()=>{}) {
    // setup AuthIdentify (tho may sometimes unnecessary).
    data.uid = user.uid;
    data.accessToken = user.accessToken;
    // request.
            console.log("request. \npath:"+path+", data:"+JSON.stringify(data));
    return axios
        .post("http://localhost:8080"+path, data)
        .then(resp => {
            console.log("request(responsed). \npath:"+path+", data:"+JSON.stringify(data)+", \nresp:"+JSON.stringify(resp.data));
            respf(resp.data);
        }).catch(ex => {
            if (ex.response) {
                if (errf == null) {
                   errf = err=>showToast("UncaughtedNetException: "+err.message)
                }
                errf(ex.response.data);
            }
        }).then(() => {
            lcall();
        });
}
var urlparams = new URLSearchParams(window.location.search);
var PATH = urlparams.get("p");


Vue.config.productionTip = false;

import AppDefine from "./main.vue";
let AppType = Vue.extend(AppDefine);
let vm = new AppType().$mount("#root");


function showToast(msg) {
    vm.pollToast(msg);
}

export {user, request, urlparams, PATH, setuplogin, showToast, updateuserinfo};