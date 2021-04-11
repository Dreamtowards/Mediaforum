<template>
<div>
    <br>
    <div class="row">
        <!-- Left Navbar -->
        <div class="col-2">

            <MainNavbar></MainNavbar>

        </div>


        <!-- Mid Content -->
        <div class="col-7">

            <!-- todo: dynamic load -->
            <div v-if="PATH == 'home'">

                <HomePostingCard></HomePostingCard>

                <HomePostpreviewCardlist></HomePostpreviewCardlist>

            </div>
            <div v-else-if="PATH == 'savelist'">

                <SavelistPage :savelist_id="parseInt(urlparams.get('list'))"></SavelistPage>

            </div>

        </div>


        <!-- Right Sidebar -->
        <div class="col-3">

            <MainUserinfoCard></MainUserinfoCard>

            <MainFooter></MainFooter>

        </div>
    </div>
</div>
</template>

<script>

import {urlparams, PATH} from "./main.js";

import MainNavbar from "./components/MainNavbar.vue";

import HomePostingCard from "./components/HomePostingCard.vue";
import HomePostpreviewCardlist from "./components/HomePostpreviewCardlist.vue";

import SavelistPage from "./components/SavelistPage.vue";

import MainUserinfoCard from "./components/MainUserinfoCard.vue";
import MainFooter from "./components/MainFooter.vue";

export default {
    components: {MainNavbar, HomePostingCard, HomePostpreviewCardlist, SavelistPage, MainUserinfoCard, MainFooter},
    data() { return {
        PATH: PATH,
        url: document.location.href,
        urlparams: urlparams,
    }},
    methods: {
        
    }
}
</script>