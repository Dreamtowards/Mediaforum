
















Vue.component("comment-edit-box", {
    props: ["post_id", "parent_comment_id", "editbox_hint"],
    template: `
<div>
    <div style="display: flex;">
        <img :src="user.avatar_url" class="rounded-circle" style="width: 38px;">
        <input type="text" class="form-control d-inline-block comment_edit" :placeholder="editbox_hint" style="margin-left: 10px;flex: 1;font-size: 96%;" onkeyup="
        $($(this).parent().parent().children()[1]).collapse($(this).val()===''?'hide':'show');">
    </div>
    <div class="collapse" style="text-align: right;">
        <div style="height: 10px;"></div>
        <button class="btn btn-primary" style="font-size: 90%;" @click="commentNew"><i class="fa fa-circle-notch fa-spin" style="font-size: 99%;margin-right: 6px;display: none;"></i>COMMENT</button>
    </div>
</div>`,
    data: function () { return {
        user: user
    }},
    methods: {
        commentNew: function (e) {
            var btn = $(e.target);
            btn.find("i").show();
            btn.prop("disabled", true);
            btn.addClass("disabled");

            request("/api/comment_new", {
                post_id: this.post_id,
                parent_comment_id: this.parent_comment_id,
                content: $($(btn.parent().parent().children()[0]).children()[1]).val()
            }, resp => {

            }, err => {
                alert(err.message);
            }, () => {
                btn.find("i").hide();
                btn.prop("disabled", false);
                btn.removeClass("disabled");
            });
        }
    }
});

Vue.component("comment-list", {
    template: `
<div>
<div style="margin-top: 14px;" v-for="comment in commentlist">
    <img :src="comment.user_avatar_url" class="rounded-circle float-left" style="width: 38px">
    <span style="font-size: 94%;font-weight: 600;margin-left: 8px;"> {{ comment.user_username }} </span><span class="text-muted" style="margin-left: 6px;font-size: 80%;">1 month ago</span>
    <div style="margin-top: 4px;font-size: 96%;margin-left: 46px;" v-html="comment.content"></div>
    <div style="margin-top: 3px;margin-left: 42px;">
        <button class="btn btn-borderless" style="font-size: 86%;"><i class="fa fa-thumbs-up"></i> <span style="font-size: 94%;font-weight: 500;"> {{  }} </span></button>
        <button class="btn btn-borderless" style="font-size: 86%;margin-bottom: -4px;margin-left: -5px;"><i class="fa fa-thumbs-down"></i></button>
        <button class="btn btn-borderless" style="font-size: 86%;margin-left: -2px;font-weight: 500;" @click="toggleReplyEdit">REPLY</button>
        <div class="collapse">
            <div style="height: 4px;"></div>
            <comment-edit-box :post_id="comment.post_id" :parent_comment_id="comment.id" :editbox_hint="'reply to ' + comment.user_username"></comment-edit-box>
            <div style="height: 10px;"></div>
        </div>
    </div>
    <div v-if="comment.replies.length != 0" style="margin-left: 42px;">
        <button class="btn none-decoration" :class="comment.parent_comment_id==0?'btn-link':'btn-borderless'" style="margin-left: 5px;padding: 0;font-size: 90%;font-weight: 500;margin-top: -2px;" @click.capture="toggleReplies">
            <i class="fa fa-caret-down" style="margin-right: 8px;"></i>See {{ comment.replies.length }} replies
        </button>
        <comment-list :commentlist="comment.replies" class="collapse"></comment-list>
    </div>
</div>
</div>`,
    props: ["post_id"],
    data() { return {
        commentlist: []
    }},
    mounted() {
        this.$nextTick(() => {
            request("/api/comment_getlist", {
                post_id: this.post_id,
            }, resp => {
                this.commentlist = resp;
            });
        });
    },
    methods: {
        toggleReplies: function (e) {
            var thisBtn = $(e.target);
            var replies = $(thisBtn.parent().children()[1]);
            var shouldShow = (replies.css("display") === "none");
            replies.collapse(shouldShow?'show':'hide');
            thisBtn.find("i").removeClass(shouldShow?"fa-caret-down":"fa-caret-up").addClass(shouldShow?"fa-caret-up":"fa-caret-down");
            thisBtn.html(shouldShow?thisBtn.html().replace("See", "Hide"):thisBtn.html().replace("Hide", "See"));  // really not good
        },
        toggleReplyEdit: function (e) {
            var thisBtn = $(e.target);
            var replyEdit = $(thisBtn.parent().children()[3]);
            var shouldShow = (replyEdit.css("display") === "none");
            replyEdit.collapse(shouldShow?'show':'hide');
            thisBtn.text(shouldShow?"CANCEL":"REPLY");  // not good
        }

    }
});