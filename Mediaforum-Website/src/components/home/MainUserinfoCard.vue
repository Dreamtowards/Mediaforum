<template>
  <div class="card">
    <div class="card-body">
      <img :src="user.avatar_url"
        style="width: 30px;height: 30px;margin-top: 6px"
        class="rounded align-top" />
      <div class="d-inline-block" style="margin-left: 5px;">
        <a class="d-block">
          <b>{{ user.username }}</b>
          <span class="badge badge-secondary" style="font-size: 10px;">Lv.3</span>
        </a>
        <small class="d-block" style="margin-top: -2px;">{{ user.email }}</small>
      </div>

      <hr />
      <!-- <p style="font-size: 88%"><i style="font-size: 92%" class="fa fa-envelope"></i> ref.drmtowards@gmail.com</p>-->
    </div>

    <hr style="margin: 8px 0;" />
    <!--<i class="fa fa-angle-right float-right" style="margin-top: 5px;"></i>-->
    <a href="#" class="btn-borderless userinfo_navs">
      <i class="fa fa-house-user"></i> &nbsp;
      <span>Your content</span>
    </a>
    <a href="#" class="btn-borderless userinfo_navs">
      <i class="fa fa-dolly"></i> &nbsp;
      <span>Paid memberships</span>
    </a>
    <hr style="margin: 8px 0;" />
    <a href="#" class="btn-borderless userinfo_navs">
      <i class="fa fa-adjust"></i> &nbsp;
      <span>Dark mode: Off</span>
      <i class="fa fa-angle-right float-right" style="margin-top: 5px;"></i>
    </a>
    <a href="#" class="btn-borderless userinfo_navs">
      <i class="fa fa-globe-americas"></i> &nbsp;
      <span>Language: English</span>
      <i class="fa fa-angle-right float-right" style="margin-top: 5px;"></i>
    </a>
    <a href="#" class="btn-borderless userinfo_navs">
      <i class="fa fa-cog"></i> &nbsp;
      <span>Settings</span>
    </a>
    <a href="#" class="btn-borderless userinfo_navs">
      <i class="fa fa-question-circle"></i> &nbsp;
      <span>Help</span>
    </a>
    <a href="#" class="btn-borderless userinfo_navs">
      <i class="fa fa-exclamation-circle"></i> &nbsp;
      <span>Send feedback</span>
    </a>
    <a href="#" class="btn-borderless userinfo_navs">
      <i class="fa fa-keyboard"></i> &nbsp;
      <span>Keyboard shortcuts</span>
    </a>

    <div style="height: 8px;"></div>
  </div>
</template>


<script>

import {user} from "../../main.js";

export default {
    data() { return {
        user: user,
    }}
}
</script>