<template>
  <div>

    <HomePostingCard></HomePostingCard>

    <HomePostpreviewCardlist></HomePostpreviewCardlist>

  </div>
</template>

<script>

import HomePostingCard from "../../HomePostingCard.vue";
import HomePostpreviewCardlist from "../../HomePostpreviewCardlist.vue";

export default {
  name: "HomeStreaming",
  components: {HomePostingCard, HomePostpreviewCardlist}
}
</script>

<style scoped>

</style>