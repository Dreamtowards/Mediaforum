<template>
  <div>
    <!-- List Header, title, info, operations -->
    <div class="card">
        <div class="card-body">
            <br>
            <h3>{{ title }}</h3>
            <p class="text-muted" style="font-size: 84%;">{{ postlist.length }} posts · Last update on </p>
            <p style="font-size: 90%;margin-top: 6px;opacity: 0.8;">No description.</p>

            <div style="margin-left: -4px;">
                <button class="btn btn-borderless"><i class="fa fa-share"></i></button>
                <button class="btn btn-borderless" data-toggle="dropdown"><i class="fa fa-ellipsis-h"></i></button>
                <div class="dropdown-menu shadow">
                    <a class="dropdown-item" href="#"><i class="fa fa-plus"></i>&nbsp;&nbsp; Add post</a>
                    <a class="dropdown-item" href="#"><i class="fa fa-cog"></i>&nbsp;&nbsp; Savelist settings</a>
                    <a class="dropdown-item" href="#" @click="submitDeleteSavelist"><i class="fa fa-trash"></i>&nbsp;&nbsp; Delete savelist</a>
                </div>
            </div>
        </div>
    </div>
    <br>

    <!-- List Items -->
    <div class="card" style="padding: 22px 20px;">
        <div v-for="(post, index) in postlist" :key="post.title">
            <h6 style="margin: 3px 0;">{{ post.title }}</h6>
            <small class="text-muted">{{ post.user_username }}</small>
            <hr v-if="index < postlist.length-1">
        </div>
    </div>
  </div>
</template>

<script>


import { request, user } from '../main.js'

export default {
    props: ["savelist_id"],
    data() { return {
        postlist: [],
        title: "",
    }},
    mounted() {
        request("/api/post_getsavelist", {
            savelist_id: this.savelist_id
        }, resp => {
            this.postlist = resp;
        });

        request("/api/savelist_getinfo", {
            savelist_id: this.savelist_id
        }, resp => {
            // let TRANSM = {"HISTORY": "History", "READLATER": "Read later", "LIKEDPOSTS": "Liked posts"}
            this.title = resp.name;
        });

    },
    methods: {
        submitDeleteSavelist() {
            if (!confirm("Are you sure needs delete savelist '"+this.title+"' ?\nthis'll remove savelist info, items. but the actual posts/contents'll not been affect.")) {
                alert("cancelled.")
                return;
            }

            request("/api/savelist_delete", {
                savelist_id: this.savelist_id
            }, resp => {
                alert("done.");
                window.location.href = "/";
            });
        }
    }
}
</script>