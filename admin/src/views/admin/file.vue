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
                <th>id</th>
        <th>相对路径</th>
        <th>文件名</th>
        <th>后缀</th>
        <th>大小</th>
        <th>用途</th>
        <th>已上传分片</th>
        <th>分片大小</th>
        <th>分片总数</th>
        <th>文件标识</th>
        <th>vod</th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="file in files">
        <td>{{file.id}}</td>
        <td>{{file.path}}</td>
        <td>{{file.name}}</td>
        <td>{{file.suffix}}</td>
        <td>{{file.size}}</td>
        <td>{{FILE_USE | optionKV(file.use)}}</td>
        <td>{{file.shardIndex}}</td>
        <td>{{file.shardSize}}</td>
        <td>{{file.shardTotal}}</td>
        <td>{{file.key}}</td>
        <td>{{file.vod}}</td>
        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <button class="btn btn-xs btn-info" v-on:click="edit(file)">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>
            <button class="btn btn-xs btn-danger" v-on:click="del(file.id)">
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
                  <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit"  v-on:click="edit(file)">
                     <span class="green">
                       <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                     </span>
                  </a>
                </li>
                <li>
                  <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" v-on:click="del(file.id)">
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
                <label class="col-sm-2 control-label">相对路径</label>
                <div class="col-sm-10">
                  <input v-model="file.path" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">文件名</label>
                <div class="col-sm-10">
                  <input v-model="file.name" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">后缀</label>
                <div class="col-sm-10">
                  <input v-model="file.suffix" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">大小</label>
                <div class="col-sm-10">
                  <input v-model="file.size" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">用途</label>
                <div class="col-sm-10">
                  <select v-model="file.use" class="form-control">
                    <option v-for="o in FILE_USE" v-bind:value="o.key">{{o.value}}</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">已上传分片</label>
                <div class="col-sm-10">
                  <input v-model="file.shardIndex" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">分片大小</label>
                <div class="col-sm-10">
                  <input v-model="file.shardSize" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">分片总数</label>
                <div class="col-sm-10">
                  <input v-model="file.shardTotal" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">文件标识</label>
                <div class="col-sm-10">
                  <input v-model="file.key" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">vod</label>
                <div class="col-sm-10">
                  <input v-model="file.vod" class="form-control">
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
  name: "business-file",
  data: function () {
    return {
      files: [],
      file: {},
      FILE_USE: FILE_USE,
    }
  },
  mounted: function () {
    let _this = this;
    _this.$refs.pagination.size = 10;
    _this.list(1);
    // sidebar激活样式方法一
    // this.$parent.activeSidebar("business-file-sidebar");
  },
  methods: {
    /**
     * 点击【新增】
     */
    add() {
      let _this = this;
      _this.file = {};
      $("#form-modal").modal("show");
    },

    /**
     * 点击【编辑】
     */
    edit(file) {
      let _this = this;
      _this.file = $.extend({},file);
      $("#form-modal").modal("show");
    },

    /**
     * 列表查询
     */
    list(page) {
      let _this = this;
      Loading.show();
      _this.$axios.get(process.env.VUE_APP_SERVER + '/business/admin/file/list', {
        params: {
          page: page,
          size: _this.$refs.pagination.size
        }
      }).then((response) => {
        Loading.hide();
        console.log("查询文件列表结果：", response);
        _this.files = response.data.data.list;
        _this.$refs.pagination.render(page, response.data.data.total);
      })
    },

    /**
     * 点击【保存】
     */
    save() {
      let _this = this;

      // 保存校验
        if (1 !== 1
          || !Validator.require(_this.file.path, "相对路径")
          || !Validator.length(_this.file.path, "相对路径", 1, 100)
          || !Validator.length(_this.file.name, "文件名", 1, 100)
          || !Validator.length(_this.file.suffix, "后缀", 1, 10)
          || !Validator.length(_this.file.key, "文件标识", 1, 32)
        ) {
          return;
        }

      Loading.show();
      _this.$axios.post(process.env.VUE_APP_SERVER + '/business/admin/file/save', _this.file).then((response) => {
        Loading.hide();
        console.log("保存文件列表结果：", response);
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
      Confirm.show("删除后将不可恢复，确认删除吗？",function (){
        Loading.show();
        _this.$axios.delete(process.env.VUE_APP_SERVER + '/business/admin/file/delete/' + id).then((response) => {
          Loading.hide();
          console.log("删除文件列表结果：", response);
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