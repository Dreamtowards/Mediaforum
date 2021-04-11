<template>
    <div class="nav flex-column nav-pills d-block" id="main_nav"> <!-- role="tablist" aria-orientation="vertical",  item: data-toggle="pill" aria-selected="false" -->
            <a class="nav-link" :class="{'active': PATH=='home'}"   href="?p=home"><i class="fa fa-home"></i>Home</a>
            <a class="nav-link" :class="{'active': PATH=='hoting'}" href="?p=hoting"><i class="fa fa-fire" style="margin: 0 13px 0 2px;"></i>Hoting</a>
            <a class="nav-link" :class="{'active': PATH=='subscriptions'}" href="?p=subscriptions"><i class="fa fa-map"></i>Subscriptions</a>
            <hr>
            <a class="nav-link" href="#"><i class="fa fa-archive"></i>Libs</a>
            <a class="nav-link" :class="{'active': PATH=='savelist'&&urlparams.get('list')==user.savelists.HISTORY.id}"    :href="'?p=savelist&list='+user.savelists.HISTORY.id"><i class="fa fa-history"></i>History</a>
            <a class="nav-link" :class="{'active': PATH=='savelist'&&urlparams.get('list')==user.savelists.READLATER.id}"  :href="'?p=savelist&list='+user.savelists.READLATER.id"><i class="fa fa-clock"></i>Read later</a>
            <a class="nav-link" :class="{'active': PATH=='savelist'&&urlparams.get('list')==user.savelists.LIKEDPOSTS.id}" :href="'?p=savelist&list='+user.savelists.LIKEDPOSTS.id"><i class="fa fa-thumbs-up"></i>Liked posts</a>

            <div ref="savelistsbox" class="collapse">
                <div style="height:6px;"></div>
                <a v-for="ucSavelist in user.savelists.created" :key="ucSavelist.id" class="nav-link" :href="'?p=savelist&list='+ucSavelist.id" :title="ucSavelist.name" style="color: #555; font-size: 92%; overflow:hidden; white-space:nowrap;text-overflow: ellipsis;background:none;"><i class="fa fa-list-alt"></i>{{ ucSavelist.name }}</a>
            </div>

            <a class="nav-link" style="margin-top: 4px;color: #454545" @click="toggleSavelists"> <i class="fa fa-angle-down"></i>&nbsp;<span>Show more</span></a>
            <hr>
            <label style="margin: 0 0 8px 16px;font-size: 80%;">SUBSCRIPTIONS</label>
            <a class="nav-link" href="#">Microsoft <sup>(Companey)</sup></a>
            <a class="nav-link" href="#">Alphabet <sup>(Companey)</sup></a>
            <a class="nav-link" href="#">FORTNE <sup>(Language)</sup></a>
            <a class="nav-link" href="#">Windows 10</a>
        </div>
</template>

<script>

import {PATH, urlparams, showToast, user} from "../main.js";
import $ from "jquery";

export default {
    data() { return {
        PATH: PATH,
        urlparams: urlparams,
        user: user
    }},
    methods: {
        toggleSavelists(e) {
            var btn = $(e.currentTarget);
            var listbox = $(this.$refs.savelistsbox);
            var shouldShow = listbox.css("display") === "none";
            
            listbox.collapse(shouldShow?"show":"hide");
            btn.find("span").text(shouldShow?"Show less":"Show more");
            btn.find("svg").attr("class", shouldShow?"fa fa-angle-up":"fa fa-angle-down")
        }
    }
}
</script>