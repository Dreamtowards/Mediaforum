<template>
  <div>
    <br />
    <br />
    <div class="row">
      <div class="col-4">
        <div class="card justify-content-center">
          <div class="card-body">
            <br />
            <h3>Sign in</h3>
            <br />
            <br />

            <div class="form-group">
              <label>Email / Username</label>
              <input type="email" class="form-control" ref="emailOrUsername" />
            </div>
            <div class="form-group">
              <label>Password</label>
              <input type="password" class="form-control" ref="password" />
            </div>

            <br />
            <button type="submit" class="btn btn-primary float-right" style="width: 100%;" @click="submitLogin">
              <i class="fas fa-circle-notch fa-spin" style="font-size: 96%;display: none;"></i>&nbsp; Login</button>
            <br />
            <br />
            <br />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import $ from "jquery";
import sha256 from "sha256";

import {request, setuplogin} from "../../main.js";

export default {
    methods: {
        submitLogin(e) {
            var btn = $(e.currentTarget);
            var icon = btn.find("svg");

            icon.show();
            btn.prop("disabled", true);
            btn.addClass("disabled");

            request("/api/user_login", {
                    account: $(this.$refs.emailOrUsername).val(),
                    password_digest: sha256($(this.$refs.password).val())
                },
                resp => {
                    setuplogin(resp.uid, resp.accessToken);
                    window.location.href = "/";
                },
                err => {
                    alert(err.message);
                    icon.hide();
                    btn.prop("disabled", false);
                    btn.removeClass("disabled");
                }
            );
        }
    }
}
</script>