<template>
<div class="row">
    <div class="col-4">
        <br />
        <br />
        <div class="card justify-content-center">
            <div class="card-body">
            <br />
            <h3>Sign up</h3>
            <br />
            <br />

            <div class="form-group">
                <label>Email</label>
                <input type="email" class="form-control" ref="email" />
                <div class="form-row" style="margin-top: 18px;">
                <div class="col-8"><input type="email" class="form-control" id="reg_email_validateCode" placeholder="Check Code"/></div>
                <div class="col-4"><button class="btn btn-primary" style="width: 100%">Send</button></div>
                </div>
            </div>
            <div class="form-group">
                <label>Username</label>
                <input type="email" class="form-control" ref="username" />
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" class="form-control" ref="password" />
                <!--                    <small class="form-text text-muted">We'll never share your email with anyone else.</small>-->
            </div>
            <div class="form-group form-check" style="font-size: 96%;">
                <input type="checkbox" class="form-check-input" id="check1" />
                <label class="form-check-label" for="check1">Agress the<a href="#">Some</a></label>
            </div>

            <br />
            <button type="submit" class="btn btn-primary float-right" style="width: 100%;" id="btn_register" @click="submitRegister">
                <i class="fas fa-circle-notch fa-spin" style="font-size: 96%;display: none;"></i>&nbsp; Register</button>
            <br />
            <br />
            <br />
            </div>
        </div>
    </div>
</div>
</template>


<script>

import $ from "jquery";
import sha256 from "sha256";

import {request, setuplogin} from "../main.js";

export default {
    methods: {
        submitRegister(e) {
            var btn = $(e.currentTarget);
            var icon = btn.find("svg");

            icon.show();
            btn.prop("disabled", true);
            btn.addClass("disabled");

            request("/api/user_register", {
                    email: $(this.$refs.email).val(),
                    username: $(this.$refs.username).val(),
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