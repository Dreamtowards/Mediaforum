<template>
    <div class="nav flex-column nav-pills d-block" id="main_nav"> <!-- role="tablist" aria-orientation="vertical",  item: data-toggle="pill" aria-selected="false" -->
      {{$route.path}}

      <router-link class="nav-link" active-class="active" to="/" exact><i class="fa fa-home"></i>Home</router-link>
      <router-link class="nav-link" active-class="active" to="/feed/trending"><i class="fa fa-fire" style="margin: 0 13px 0 2px;"></i>Trending</router-link>
      <router-link class="nav-link" active-class="active" to="/feed/subscriptions"><i class="fa fa-map"></i>Subscriptions</router-link>
      <hr>
      <router-link class="nav-link" active-class="active" to="/feed/library"><i class="fa fa-archive"></i>Libs</router-link>
      <router-link class="nav-link" active-class="active" :to="{path: '/savelist', query: {list: user.savelists.HISTORY.id}}" exact><i class="fa fa-history"></i>History</router-link>
      <router-link class="nav-link" active-class="active" :to="{path: '/savelist', query: {list: user.savelists.READLATER.id}}" exact><i class="fa fa-clock"></i>Read later</router-link>
      <router-link class="nav-link" active-class="active" :to="{path: '/savelist', query: {list: user.savelists.LIKEDPOSTS.id}}" exact><i class="fa fa-thumbs-up"></i>Liked posts</router-link>

      <div ref="savelistsbox" class="collapse">
          <div style="height:6px;"></div>
          <router-link v-for="ucSavelist in user.savelists.created" :key="ucSavelist.id" class="nav-link" active-class="active" :to="{path: '/savelist', query: {list: ucSavelist.id}}" :title="ucSavelist.name" style="color: #555; font-size: 92%; overflow:hidden; white-space:nowrap;text-overflow: ellipsis;background:none;"><i class="fa fa-list-alt"></i>{{ ucSavelist.name }}</router-link>
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

import {showToast, user} from "../../../main.js";
import $ from "jquery";

export default {
  props: ["curr_page"],
  data() { return {
    user: user
  }},
  methods: {
      toggleSavelists(e) {
          let btnToggle = $(e.currentTarget);
          let listbox = $(this.$refs.savelistsbox);
          let shouldshow = listbox.css("display") === "none";

          listbox.collapse(shouldshow?"show":"hide");
          btnToggle.find("span").text(shouldshow?"Show less":"Show more");
          btnToggle.find("svg").attr("class", shouldshow?"fa fa-angle-up":"fa fa-angle-down")
      }
  }
}
</script>