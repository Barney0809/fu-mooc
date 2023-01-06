<template>
  <div>
    <h4 class="lighter">
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/admin/business/course" class="pink"> {{ course.name }}</router-link>
      ：
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/admin/business/chapter" class="pink"> {{ chapter.name }}</router-link>
    </h4>
    <hr>
    <p>
      <button v-on:click="add()" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-edit"></i>
        新增
      </button>
      &nbsp;
      <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
    </p>
    <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8"></pagination>
    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>id</th>
        <th>标题</th>
        <th>视频</th>
        <th>时长</th>
        <th>收费</th>
        <th>顺序</th>
        <th>vod</th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="section in sections">
        <td>{{ section.id }}</td>
        <td>{{ section.title }}</td>
        <td>{{ section.video }}</td>
        <td>{{ section.time | formatSecond }}</td>
        <td>{{ SECTION_CHARGE | optionKV(section.charge) }}</td>
        <td>{{ section.sort }}</td>
        <td>{{ section.vod }}</td>
        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <button class="btn btn-xs btn-info" v-on:click="edit(section)">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>
            <button class="btn btn-xs btn-danger" v-on:click="del(section.id)">
              <i class="ace-icon fa fa-trash-o bigger-120"></i>
            </button>
          </div>

          <div class="hidden-md hidden-lg">
            <div class="inline pos-rel">
              <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
              </button>

              <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                <li>
                  <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit" v-on:click="edit(section)">
                     <span class="green">
                       <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                     </span>
                  </a>
                </li>
                <li>
                  <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" v-on:click="del(section.id)">
                     <span class="red">
                       <i class="ace-icon fa fa-trash-o bigger-120"></i>
                     </span>
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </td>
      </tr>
      </tbody>
    </table>
    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">表单</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-2 control-label">标题</label>
                <div class="col-sm-10">
                  <input v-model="section.title" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">课程</label>
                <div class="col-sm-10">
                  <p class="form-control-static">{{ course.name }}</p>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">大章</label>
                <div class="col-sm-10">
                  <p class="form-control-static">{{ chapter.name }}</p>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">视频</label>
                <div class="col-sm-10">
                  <vod v-bind:input-id="'video-upload'"
                            v-bind:text="'上传VOD'"
                            v-bind:suffixs="['mp4']"
                            v-bind:use="FILE_USE.COURSE.key"
                            v-bind:after-upload="afterUpload"></vod>
                  <div v-show="section.video" class="row">
                    <div class="col-md-9">
                      <player ref="player"></player>
                      <video v-bind:src="section.video" id="video" controls="controls"></video>
                    </div>
                  </div>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">时长</label>
                <div class="col-sm-10">
                  <input v-model="section.time" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">视频</label>
                <div class="col-sm-10">
                  <input v-model="section.video" class="form-control" disabled>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">VOD</label>
                <div class="col-sm-10">
                  <input v-model="section.vod" class="form-control" disabled>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">收费</label>
                <div class="col-sm-10">
                  <select v-model="section.charge" class="form-control">
                    <option v-for="o in SECTION_CHARGE" v-bind:value="o.key">{{ o.value }}</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">顺序</label>
                <div class="col-sm-10">
                  <input v-model="section.sort" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">vod</label>
                <div class="col-sm-10">
                  <input v-model="section.vod" class="form-control">
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" v-on:click="save()">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
  </div>
</template>

<script>
import Pagination from "@/components/pagination";
import Vod from "@/components/vod.vue";
import Player from "@/components/player.vue";

export default {
  components: {Player, Vod, Pagination},
  name: "business-section",
  data: function () {
    return {
      sections: [],
      section: {},
      SECTION_CHARGE: SECTION_CHARGE,
      FILE_USE: FILE_USE,
      course: {},
      chapter: {},
    }
  },
  mounted: function () {
    let _this = this;
    _this.$refs.pagination.size = 10;
    let course = SessionStorage.get(SESSION_KEY_COURSE) || {};
    let chapter = SessionStorage.get(SESSION_KEY_CHAPTER) || {};
    if (Tool.isEmpty(course) || Tool.isEmpty(chapter)) {
      _this.$router.push("/admin/welcome");
    } else {
      _this.course = course;
      _this.chapter = chapter;
      _this.list(1);
      // sidebar激活样式方法一
      this.$parent.activeSidebar("business-course-sidebar");
    }

    // sidebar激活样式方法一
    // this.$parent.activeSidebar("business-section-sidebar");
  },
  methods: {
    /**
     * 点击【新增】
     */
    add() {
      let _this = this;
      _this.section = {};
      $("#form-modal").modal("show");
    },

    /**
     * 点击【编辑】
     */
    edit(section) {
      let _this = this;
      _this.section = $.extend({}, section);
      $("#form-modal").modal("show");
    },

    /**
     * 列表查询
     */
    list(page) {
      let _this = this;
      Loading.show();
      _this.$axios.get(process.env.VUE_APP_SERVER + '/business/admin/section/list', {
        params: {
          page: page,
          size: _this.$refs.pagination.size,
          courseId: _this.course.id,
          chapterId: _this.chapter.id
        }
      }).then((response) => {
        Loading.hide();
        console.log("查询小节列表结果：", response);
        _this.sections = response.data.data.list;
        _this.$refs.pagination.render(page, response.data.data.total);
      })
    },

    /**
     * 点击【保存】
     */
    save() {
      let _this = this;

      _this.section.video = "";
      // 保存校验
      if (1 !== 1
          || !Validator.require(_this.section.title, "标题")
          || !Validator.length(_this.section.title, "标题", 1, 50)
          || !Validator.length(_this.section.video, "视频", 1, 200)
      ) {
        return;
      }

      _this.section.courseId = _this.course.id;
      _this.section.chapterId = _this.chapter.id;
      Loading.show();
      _this.$axios.post(process.env.VUE_APP_SERVER + '/business/admin/section/save', _this.section).then((response) => {
        Loading.hide();
        console.log("保存小节列表结果：", response);
        if (response.data.code === "0") {
          $("#form-modal").modal("hide");
          _this.list(1);
          Toast.success("保存成功！");
        } else {
          Toast.warning(response.data.message);
        }
      })
    },

    /**
     * 点击【删除】
     */
    del(id) {
      let _this = this;
      Confirm.show("删除后将不可恢复，确认删除吗？", function () {
        Loading.show();
        _this.$axios.delete(process.env.VUE_APP_SERVER + '/business/admin/section/delete/' + id).then((response) => {
          Loading.hide();
          console.log("删除小节列表结果：", response);
          if (response.data.code === "0") {
            _this.list(1);
            Toast.success("删除成功！")
          }
        })
      });
    },

    afterUpload(response) {
      let _this = this;
      _this.section.video = response.data.path;
      _this.section.vod = response.data.vod;
      _this.$forceUpdate();
      _this.getTime();
      _this.$refs.player.playUrl(_this.section.video);
    },

    /**
     * 获取时长
     */
    getTime() {
      let _this = this;
      setTimeout(function () {
        let ele = document.getElementById("video");
        _this.section.time = parseInt(ele.duration, 10);
      }, 100);
    },
  }
}
</script>

<style scoped>
video {
  width: 100%;
  height: auto;
  margin-top: 10px;
}
</style>