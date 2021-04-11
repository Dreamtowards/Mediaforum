<template>
  <div>
    <!-- nav bar -->
    <nav class="navbar navbar-expand-md">  <!-- shadow-sm -->
      <div class="container">
        <div class="form-row" style="width: 100%;margin: 5px 0 -8px 0;">
          <div class="col-2">
            <a href="#" class="navbar-brand">
              <!-- <i class="fa fa-eye"></i>&nbsp; -->
              <b>Mediaforum</b>
            </a>
          </div>
          <div class="col-7">
            <!-- <div> -->
              <!-- <input type="text" class="form-control" style="margin-top: 2px;padding: 22px 22px 22px 46px;border: none;background: rgb(244,244,244);" placeholder="Search" > -->
              <!-- <i class="fa fa-search" style="float: left;margin-top: -30px;margin-left: 16px;opacity: 0.6;"></i> -->
            <!-- </div> -->
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

    <div ref="notifyBox" class="shadow-sm collapse" style="position: fixed;right: 16px;bottom: 40px;z-index: 1;width: 250px;background: #454545;color: #fff;font-weight: 300;font-size: 88%;border-radius: 3px;">
      <div style="height: 12px;"></div>
      <span style="margin-left: 16px;">Some Notification Messages</span>
      <div style="height: 12px;"></div>
    </div>

    <!-- content -->
    <div id="main_content" class="container">

      <!-- these register/login 'll been split out in othe site -->
      <SignRegister v-if="PATH === 'register'"></SignRegister>
      <SignLogin    v-else-if="PATH === 'login'"></SignLogin>
      <HomeLayout   v-else></HomeLayout>
    
    </div>
  </div>
</template>

<script>
import HomeLayout from "./homelayout.vue";
import {user, PATH} from "./main.js";

// just a tmp way, for sign in, should had a indie site.
import SignRegister from "./components/SignRegister.vue";
import SignLogin from "./components/SignLogin.vue";


import $ from "jquery";

export default {
  components: { HomeLayout, SignRegister, SignLogin },
  data() { return {
      user: user,
      PATH: PATH
  }},
  methods: {
    pollToast(msg) {
      var notifyBox = $(this.$refs.notifyBox);
      notifyBox.collapse("show");
      notifyBox.find("span").text(msg);
      setTimeout(() => {
          if (notifyBox.find("span").text() == msg) {
            notifyBox.collapse("hide");
          }
      }, 2500);
    }
  }
};
</script>