<template>
  <div class="card">
    <div class="card-body">
      <div class="form-row collapse" id="post_titleBox">
        <div class="col-10">
          <input type="text" class="form-control" placeholder="Title" id="post_title" />
        </div>
        <div class="col-2">
          <select class="form-control">
            <option value="public">Public</option>
            <option value="friendly">Friendly</option>
            <option value="private">Private</option>
          </select>
        </div>
        <br />
        <div style="height: 12px;width: 100%;"></div>
      </div>

      <textarea class="form-control" id="post_content_textarea" placeholder="Whts new?" @keyup="toggleTitleEdit"></textarea>
      <br/>

      <img :src="user.avatar_url" style="width: 28px;height: 28px;" class="rounded-circle" />

      <button class="btn btn-primary float-right" id="post_submit" @click="submitPost">
        <i id="post_submit_icon" class="fa fa-plus" style="font-size: 90%;"></i> Post
      </button>
    </div>
  </div>
</template>



<script>

import $ from "jquery";

import {request, user} from "../main.js";

export default {
    data() { return {
        user: user
    }},
    methods: {
        toggleTitleEdit(e) {
            // when content-input is't empty, show title edit box.
            $("#post_titleBox").collapse($(e.target).val().trim()===""? "hide":"show");
        },
        submitPost(e) {
            let btn = $(e.target);
            
            $("#post_submit_icon").removeClass("fa-plus").addClass("fa-circle-notch").addClass("fa-spin");
            btn.prop("disabled", true).addClass("disabled");

            request("/api/post_new", {
                    title: $("#post_title").val(),
                    content: $("#post_content_textarea").val()
                }, resp => {
                    alert("success!, postId=" + resp.post_id);
                }, err => {
                    alert("failed. " + err.message);
                }, () => {
                    $("#post_submit_icon").removeClass("fa-circle-notch").removeClass("fa-spin").addClass("fa-plus");
                    btn.prop("disabled", false).removeClass("disabled");
                }
            );
        }
    }
}
</script>