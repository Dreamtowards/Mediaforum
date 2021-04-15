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

          <router-view></router-view>

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

import MainNavbar from "./navbar/MainNavbar.vue";

import MainUserinfoCard from "./MainUserinfoCard.vue";
import MainFooter from "./MainFooter.vue";

export default {
    components: {MainNavbar, MainUserinfoCard, MainFooter},
    data() { return {

    }},
    methods: {
        
    }
}
</script>