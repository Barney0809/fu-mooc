<template>
  <div>
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
        <th>ID</th>
        <th>名称</th>
        <th>课程ID</th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="chapter in chapters">
        <td>{{ chapter.id }}</td>
        <td>{{ chapter.name }}</td>
        <td>{{ chapter.courseId }}</td>
        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <button class="btn btn-xs btn-info" v-on:click="edit(chapter)">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>
            <button class="btn btn-xs btn-danger" v-on:click="del(chapter.id)">
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
                  <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit"  v-on:click="edit(chapter)">
                     <span class="green">
                       <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                     </span>
                  </a>
                </li>
                <li>
                  <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" v-on:click="del(chapter.id)">
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
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                  <input v-model="chapter.name" class="form-control" placeholder="名称">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">课程ID</label>
                <div class="col-sm-10">
                  <input v-model="chapter.courseId" class="form-control" placeholder="课程ID">
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

export default {
  components: {Pagination},
  name: "chapter",
  data: function () {
    return {
      chapters: [],
      chapter: {}
    }
  },
  mounted: function () {
    let _this = this;
    _this.$refs.pagination.size = 10;
    _this.list(1);
    // sidebar激活样式方法一
    // this.$parent.activeSidebar("business-chapter-sidebar");
  },
  methods: {
    add() {
      let _this = this;
      _this.chapter = {};
      $("#form-modal").modal("show");
    },
    edit(chapter) {
      let _this = this;
      _this.chapter = $.extend({},chapter);
      $("#form-modal").modal("show");
    },
    list(page) {
      let _this = this;
      Loading.show();
      _this.$axios.get('http://127.0.0.1:9000/business/admin/chapter/list', {
        params: {
          page: page,
          size: _this.$refs.pagination.size
        }
      }).then((response) => {
        Loading.hide();
        console.log("查询大章列表结果：", response);
        _this.chapters = response.data.data.list;
        _this.$refs.pagination.render(page, response.data.data.total);
      })
    },
    save(page) {
      let _this = this;
      Loading.show();
      _this.$axios.post('http://127.0.0.1:9000/business/admin/chapter/save', _this.chapter).then((response) => {
        Loading.hide();
        console.log("保存大章列表结果：", response);
        if (response.data.code === "0") {
          $("#form-modal").modal("hide");
          _this.list(1);
          Toast.success("保存成功！");
        }
      })
    },
    del(id) {
      let _this = this;
      Confirm.show("删除后将不可恢复，确认删除吗？",function (){
        Loading.show();
        _this.$axios.delete('http://127.0.0.1:9000/business/admin/chapter/delete/' + id).then((response) => {
          Loading.hide();
          console.log("删除大章列表结果：", response);
          if (response.data.code === "0") {
            _this.list(1);
            Toast.success("删除成功！")
          }
        })
      });
    }
  }
}
</script>