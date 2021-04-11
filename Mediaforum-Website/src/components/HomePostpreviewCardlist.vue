<template>
  <div id="post_preview_cards">
    <div class="card" style="margin-top: 12px;" v-for="(post, index) in postList" :key="post.id">
      <div class="card-body">
        <button class="btn btn-borderless float-right" style="padding: 2px;margin-top: -4px;" title="Hide">
          <i class="fa fa-times"></i>
        </button>
        <!-- <div style="margin: 2px 0 12px 0;">
          <img :src="post.user_avatar_url" class="rounded-circle" style="width: 32px;">&nbsp;
          <span style="font-size: 82%;color: #444;">{{ post.user_username }} · 1 month ago</span>
        </div> -->
        <h5 class="d-inline-block">{{ post.title }}</h5>
        <p class="text-muted" style="font-size: 83%;margin-top: -2px;margin-bottom: 14px;">Posted from {{ post.user_username }} · 1 days ago.</p>


        <div v-html="post.content" class="post_preview_card_content"></div>

        <div>
          <button class="btn" :class="post.was_liked?'btn-primary':'btn-light'" @click="submitLike(post)">
            <i class="fa fa-caret-up"></i>
            <span style="font-size: 15px;font-weight: 600;">&nbsp; {{ post.likes_count }} &nbsp;</span>
          </button>
          <button class="btn btn-light">
            <i class="fa fa-caret-down"></i>
          </button>

          <button
            class="btn btn-borderless"
            style="font-size: 85%;font-weight: bold;margin-left: 6px;"
            @click.prevent="updateCommentList(index, $event)">
            <i class="fa fa-comment-alt" style="font-size: 87%;"></i> {{ post.cache_commentsShown?" &nbsp;Collapse up": post.comment_count+" Comments" }}
          </button>
          <button class="btn btn-borderless" style="font-size: 85%;font-weight: bold;" @click="sharePost">
            <i class="fa fa-share" style="font-size: 94%;"></i> Share
          </button>
          <button class="btn btn-borderless" style="font-size: 85%;font-weight: bold;" @click="savePost($event, post)">
            <i class="fa fa-bookmark" style="font-size: 94%;"></i> Save
          </button>
          <button class="btn btn-borderless" style="font-size: 85%;font-weight: bold;">
            <i class="fa fa-flag" style="font-size: 94%;"></i> Report
          </button>
          <button class="btn btn-borderless" style="font-size: 90%;">
            <i class="fa fa-ellipsis-h"></i>
          </button>
        </div>

        <div class="comments-list collapse">
          <hr/>
          <div>
            <CommentEditBox 
            :post_id="post.id" 
            :parent_comment_id="0" 
            :_editbox_hint="'Add a comment...'" 
            :_insertcommentlist="post.cache_commentlist"></CommentEditBox>

            <div style="margin-top: 30px;"></div>
            <div v-if="post.cache_commentlist==null" style="text-align: center;"><br><i class="fa-2x fas fa-circle-notch fa-spin" style="opacity: 0.8;"></i><br><br><br></div>
            <CommentList :commentlist="post.cache_commentlist" :post_id="post.id"></CommentList>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal: SavelistOp -->
    <!-- <div class="modal fade" ref="postmodal_savelistop" tabindex="-1" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable" style="transition: none;transform: none;">
        <div class="modal-content card shadow-lg" style="border: none;border-radius: 3px;width: 60%;">
          <div class="card-body">

            <button class="btn btn-borderless float-right" data-dismiss="modal" style="margin: -8px -4px;"><i class="fa fa-times"></i></button>
            <p style="font-size: 18px;font-weight: 500;">Savelists</p>

            <div v-if="currop_savelists===null" style="text-align: center;"><br><br><i class="fa-2x fas fa-circle-notch fa-spin" style="opacity: 0.8;"></i><br><br><br></div>
            
            <div v-for="savelist in currop_savelists" :key="savelist.id" style="padding: 14px 0;">
              <h6 class="d-inline-block">{{ savelist.name }}</h6>
              <button class="btn float-right" :class="savelist.contained_post?'btn-light':'btn-primary'" style="padding: 4px 14px;margin-top: -5px;" @click="submitSavelistAdditem($event, savelist)">
                <span v-show="savelist.contained_post"><i class="fa fa-minus"></i></span>
                <span v-show="!savelist.contained_post"><i class="fa fa-plus"></i></span>
              </button>
            </div>
            <hr>
            <button class="btn btn-secondary" style="width: 100%;font-size: 98%;" @click="showCreateSavelistBox"><i class="fa fa-plus" style="font-size: 90%;"></i>&nbsp; <span>Create a savelist</span></button>
            <div ref="create_savelist_box" class="collapse">
              <br>
              <input type="text" class="form-control" placeholder="Savelist name..." ref="createsavelist_name">
              <br>
              <select class="form-control" disabled>
                <option>Public</option>
                <option>Private</option>
              </select>
              <br>
              <button class="btn btn-primary float-right" style="margin-top: 10px;" @click="submitCreateSavelist"><i class="fas fa-circle-notch fa-spin" style="font-size: 93%;display: none;margin-right: 4px;"></i>Create</button>
            </div>
          </div>
        </div>
      </div>
    </div> -->

    <DialogSavelistOpPost ref="dialogSavelistOpPost"></DialogSavelistOpPost>

    <DialogSharePost ref="dialogSharePost"></DialogSharePost>

    <br />
    <br />
  </div>
</template>


<script>

import {request, showToast, user, updateuserinfo} from "../main.js";
let markdownit = require("markdown-it")();

import $ from "jquery";

import CommentEditBox from "./CommentEditBox.vue";
import CommentList from "./CommentList.vue";

import DialogSharePost from "./DialogSharePost.vue";
import DialogSavelistOpPost from "./DialogSavelistOpPost.vue";

export default {
    components: {CommentEditBox, CommentList, DialogSharePost, DialogSavelistOpPost},
    data() { return {
        postList: [],
    }}, 
    mounted () {
      request("/api/post_getfeedlist", {}, resp => {
        this.postList = [];
        resp.forEach((post, i) => {
          this.postList.push({
            id: post.id,
            title: post.title,
            likes_count: post.liked_count,
            was_liked: post.was_liked,
            comment_count: post.comment_count,
            content: markdownit.render(post.content),
            user_username: post.user_username,
            user_avatar_url: post.user_avatar_url,

            cache_commentlist: null,
            cache_commentsShown: false,
          });
        });
      });
    }, 
    methods: {
      updateCommentList(postlistIndex, e) {
        let btn = $(e.currentTarget);
        var postItem = this.postList[postlistIndex];

        postItem.cache_commentsShown = !postItem.cache_commentsShown;

        if (postItem.cache_commentsShown) {
          // postItem.cache_commentlist = null;  // when show commentsList, clear (and then refresh actually content.
          request("/api/comment_getlist", { post_id: postItem.id }, resp => {
              postItem.cache_commentlist = resp;
          });
        }

        $(btn.parent().parent().find(".comments-list")).collapse(postItem.cache_commentsShown?"show":"hide");
      },
      sharePost(e) {
        this.$refs.dialogSharePost.showDialog()
      },
      submitLike(post) {
        post.was_liked = !post.was_liked;

        post.likes_count += post.was_liked?1:-1;

        request("/api/savelist_opitem", {
          op: post.was_liked?"add":"remove",
          savelist_id: user.savelists.LIKEDPOSTS.id,
          post_id: post.id
        }, resp => {
          showToast(post.was_liked?"Added to likedposts.":"Removed from likedposts.");
        });
      },
      savePost(e, post) {
        this.$refs.dialogSavelistOpPost.show(post);
      }
    }
};
</script>