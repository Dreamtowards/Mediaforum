<template>
  <div>
    <!-- nav bar -->
    <nav class="navbar navbar-expand-md">  <!-- shadow-sm -->
      <div class="container">
        <div class="form-row" style="width: 100%;margin: 5px 0 -8px 0;">
          <div class="col-2">
            <a href="/" class="navbar-brand">
              <!-- <i class="fa fa-eye"></i>&nbsp; -->
              <b>Mediaforum</b>
            </a>
          </div>
          <div class="col-7">
<!--             <div>-->
<!--               <input type="text" class="form-control" style="margin-top: 2px;padding: 22px 22px 22px 46px;border: none;background: rgb(244,244,244);" placeholder="Search" >-->
<!--               <i class="fa fa-search" style="float: left;margin-top: -30px;margin-left: 16px;opacity: 0.6;"></i>-->
<!--             </div> -->
          </div>
          <div class="col-3">
            <ul class="nav float-right" style="margin-right: -8px">
              <!-- <li class="nav-item"><a class="nav-link" href="#">Home</a></li> -->
              <!-- <li class="nav-item"><a class="nav-link" href="#">Subs</a></li> -->
              <!-- <li class="nav-item"><a class="nav-link" href="#">Libs</a></li> -->
            </ul>
            <!-- <img :src="user.avatar_url" style="width: 28px;height: 28px;" class="rounded-circle"/> -->
          </div>
        </div>
      </div>
    </nav>

    <!-- content -->
    <div id="main_content" class="container">

      <router-view></router-view>
    
    </div>

    <div ref="notifyBoxList" style="position: fixed;right: 16px;bottom: 40px;z-index: 1;width: 250px;">

    </div>
  </div>
</template>

<script>
import {user} from "./main.js";

import $ from "jquery";

export default {
  components: { },
  data() { return {
      user: user
  }},
  methods: {
    pollToast(msg) {
      let notifyBoxList = this.$refs.notifyBoxList;

      let ele = document.createElement('div');
      ele.innerHTML =
          '<div class="shadow-sm collapse" style="background: #454545;color: #fff;font-weight: 300;font-size: 88%;border-radius: 3px;margin-top: 4px;">'+
          '<div style="height: 12px;"></div>'+
          '<span style="margin-left: 16px;">Some Notification Messages</span>'+
          '<div style="height: 12px;"></div>'+
          '</div>';
      notifyBoxList.appendChild(ele);

      let box = $(ele.children[0]);
      box.collapse("show");
      box.find("span").text(msg);
      setTimeout(() => {
        box.collapse("hide");
      }, 5000);
    }
  }
};
</script>