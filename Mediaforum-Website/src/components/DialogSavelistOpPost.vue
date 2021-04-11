<template>
    <!-- Modal: SavelistOp -->
    <div class="modal fade" ref="dialog" tabindex="-1" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable" style="transition: none;transform: none;">
        <div class="modal-content card shadow-lg" style="border: none;border-radius: 3px;width: 60%;">
          <div class="card-body">

            <button class="btn btn-borderless float-right" data-dismiss="modal" style="margin: -8px -4px;"><i class="fa fa-times"></i></button>
            <p style="font-size: 18px;font-weight: 500;">Savelists</p>

            <div v-if="op_savelists===null" style="text-align: center;"><br><br><i class="fa-2x fas fa-circle-notch fa-spin" style="opacity: 0.8;"></i><br><br><br></div>

            <div v-for="savelist in op_savelists" :key="savelist.id" style="padding: 14px 0;">
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
    </div>
</template>

<script>

import $ from "jquery";

import { user, request, showToast } from "../main.js";

export default {
  data() { return {
    post_id: 0,
    op_savelists: []
  }},
  methods: {
    show(post) {
      $(this.$refs.dialog).modal("show");
      this.post_id = post.id;

      this.op_savelists = null;  // display none. before already.

      // init display savelists
      var savelist_ids = []; // for api params
      let op_savelists = []; // local var
      [user.savelists.READLATER].concat(user.savelists.created).forEach(savelist => {
        savelist_ids.push(savelist.id);
        op_savelists.push({
          id: savelist.id,
          name: savelist.name,
          contained_post: false
        });
      });

      // checks each savelist is contains curr this post.
      request("/api/savelist_containspost", {
        savelist_ids: savelist_ids,
        post_id: post.id,
      }, resp => {
        resp.forEach(containedId => {
          op_savelists.forEach(opsavelist => {
            if (opsavelist.id === containedId) {  // contained.
              opsavelist.contained_post = true; // and break;
            }
          });
        });

        this.op_savelists = op_savelists;

      });
    },
    showCreateSavelistBox(e) {
      let btn = $(e.currentTarget);
      let box = $(this.$refs.create_savelist_box);
      let show = box.css("display") == "none";

      box.collapse(show?"show":"hide");
      btn.find("svg").css("display", show?"none":"inline-block");
      btn.find("span").text(show?"Cancel":"Create a savelist");
    },
    submitCreateSavelist(e) {
      let btn = $(e.currentTarget);
      var icon = btn.find("svg");
      let inputName = $(this.$refs.createsavelist_name);
      let name = inputName.val();

      icon.show();
      btn.prop("disabled", true);
      btn.addClass("disabled");

      request("/api/savelist_new", {
        name: name,
      }, resp => {
        showToast("Created a new savelist");
        updateuserinfo();
        inputName.val("");
        this.op_savelists.splice(1, 0, {
          id: resp.id,
          name: name,
          contained_post: false,
        });
      }, null, () => {
        icon.hide();
        btn.prop("disabled", false);
        btn.removeClass("disabled");
      });
    },
    submitSavelistAdditem(e, opsavelist) {
      opsavelist.contained_post = !opsavelist.contained_post;

      request("/api/savelist_opitem", {
        op: opsavelist.contained_post?"add":"remove",
        savelist_id: opsavelist.id,
        post_id: this.post_id
      }, resp => {
        showToast(opsavelist.contained_post?"Added to the savelist.":"Removed from the savelist.");
      });
    }
  }
}
</script>