<template>
  <div>
    <div style="margin-top: 14px;" v-for="comment in commentlist" :key="comment.id">
      <img :src="comment.user_avatar_url" class="rounded-circle float-left" style="width: 38px;margin-top: 3px;" />
      <span style="font-size: 94%;font-weight: 600;margin-left: 10px;">{{ comment.user_username }}</span>
      <span class="text-muted" style="margin-left: 6px;font-size: 80%;">1 month ago</span>
      <div style="margin-top: 4px;font-size: 96%;margin-left: 48px;" v-html="comment.content"></div>
      <div style="margin-top: 3px;margin-left: 44px;">
        <button class="btn btn-borderless" style="font-size: 86%;"><i class="fa fa-thumbs-up"></i><span style="font-size: 94%;font-weight: 500;">{{ }}</span></button>
        <button class="btn btn-borderless" style="font-size: 86%;margin-bottom: -4px;margin-left: -5px;"><i class="fa fa-thumbs-down"></i></button>
        <button class="btn btn-borderless" style="font-size: 86%;margin-left: -2px;font-weight: 500;" @click="toggleReplyEdit">REPLY</button>
        <div class="collapse">
          <div style="height: 4px;"></div>
          <CommentEditBox
            :post_id="post_id"
            :parent_comment_id="comment.id"
            :_editbox_hint="'reply to ' + comment.user_username"
            :_insertcommentlist="comment.replies"></CommentEditBox>
          <div style="height: 10px;"></div>
        </div>
      </div>
      <div v-if="comment.replies.length != 0" style="margin-left: 44px;">
        <button
          class="btn none-decoration"
          :class="comment.parent_comment_id==0?'btn-link':'btn-borderless'"
          style="margin-left: 5px;padding: 0;font-size: 90%;font-weight: 500;margin-top: -2px;"
          @click="toggleReplies">
            <i class="fa fa-caret-down" style="margin-right: 8px;"></i>
            <span>See {{ comment.replies.length }} replies</span>
        </button>
        <CommentList :commentlist="comment.replies" :post_id="post_id" class="collapse"></CommentList>
      </div>
    </div>
  </div>
</template>

<script>

import CommentEditBox from "./CommentEditBox.vue";

import $ from "jquery";

export default {
  components: {CommentEditBox},
  name: "CommentList",
  props: ["commentlist", "post_id"],
  data() { return {

  }},
  methods: {
    toggleReplies(e) {
      var btn = $(e.currentTarget);
      var replies = $(btn.parent().children()[1]);
      var shouldShow = replies.css("display") === "none";
      replies.collapse(shouldShow ? "show" : "hide");

      btn.find("svg").attr("class", shouldShow?"fa fa-caret-up":"fa fa-caret-down");

      let textspan = btn.find("span");
      textspan.text(
        shouldShow
          ? textspan.text().replace("See", "Hide")
          : textspan.text().replace("Hide", "See")
      ); // really not good
    },
    toggleReplyEdit(e) {
      var btn = $(e.target);
      var editbox = $(btn.parent().children()[3]);
      var shouldShow = editbox.css("display") === "none";
      editbox.collapse(shouldShow ? "show" : "hide");
      btn.text(shouldShow ? "CANCEL" : "REPLY"); // not good
    }
  }
};

</script>