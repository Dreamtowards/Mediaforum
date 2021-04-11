<template>
  <div>
    <!-- Modal: Share -->
    <div class="modal fade" ref="dialog" tabindex="-1" aria-hidden="true">
      <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered" style="transition: none;transform: none;">
        <div class="modal-content card shadow-lg" style="border: none;border-radius: 3px;width: 90%;">
          <div class="card-body">
            <button class="btn btn-borderless float-right" data-dismiss="modal" style="margin: -8px -4px;"><i class="fa fa-times"></i></button>
            <p style="font-size: 18px;font-weight: 500;">Share</p>

            <input type="text" class="form-control" style="font-size: 94%;" :value="share_link_url" disabled/>

            <button class="btn btn-link none-decoration float-right" style="margin-top: -36px;font-weight: 500;font-size: 88%;">COPY</button>
            <hr />
            <div></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import $ from "jquery";

export default {
  data() { return {
    share_link_url: "http://localhost.com/p/23132380283"
  }},
  methods: {
    showDialog() {
      $(this.$refs.dialog).modal("show");
    }
  }
};
</script>