<template>
  <div>
    <div style="display: flex;">
      <img :src="user.avatar_url" class="rounded-circle" style="width: 38px;height:38px;" />
      <textarea ref="editbox" type="text" class="form-control d-inline-block comment_edit" style="margin-left: 10px;flex: 1;font-size: 96%;"
        :placeholder="_editbox_hint"
        @keyup="toggleSubmitBtn"></textarea>
    </div>
    <div class="collapse" style="text-align: right;">
      <div style="height: 10px;"></div>
      <button class="btn btn-primary" style="font-size: 90%;" @click="commentNew">
        <i class="fa fa-circle-notch fa-spin" style="font-size: 99%;margin-right: 6px;display: none;"></i>COMMENT
      </button>
    </div>
  </div>
</template>

<script>

import $ from "jquery";

import {user, request} from "../main.js";

export default {
    props: ["post_id", "parent_comment_id", "_editbox_hint", "_insertcommentlist"],
    data() { return {
        user: user
    }},
    methods: {
        toggleSubmitBtn(e) {
            let input = $(e.target);
            $(input.parent().parent().children()[1]).collapse(input.val()===''?'hide':'show');
        },
        commentNew(e) {
            var btn = $(e.target);
            btn.find("svg").show();
            btn.prop("disabled", true);
            btn.addClass("disabled");

            let commentContent = $($(btn.parent().parent().children()[0]).children()[1]).val();
                
            request("/api/comment_new", {
                post_id: this.post_id,
                parent_comment_id: this.parent_comment_id,
                content: commentContent
            }, resp => {
                // clear the textarea
                this.$refs.editbox.value = "";

                // insert a comment item, for display
                this._insertcommentlist.splice(0, 0, {
                  replies: [],
                  user_id: user.id,
                  user_username: user.username,
                  user_avatar_url: user.avatar_url,
                  id: resp.id,
                  parent_comment_id: this.parent_comment_id,
                  post_time: Date.now(),
                  content: commentContent,
                });
            }, err => {
                alert(err.message);
            }, () => {
                btn.find("svg").hide();
                btn.prop("disabled", false);
                btn.removeClass("disabled");
            });
        }
    }
}
</script>