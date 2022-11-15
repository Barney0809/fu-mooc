<template>
  <div>
    <p>
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
      </tr>
      </thead>

      <tbody>
      <tr v-for="file in files">
        <td>{{file.id}}</td>
        <td>{{file.path}}</td>
        <td>{{file.name}}</td>
        <td>{{file.suffix}}</td>
        <td>{{file.size | formatFileSize}}</td>
        <td>{{FILE_USE | optionKV(file.use)}}</td>
        <td>{{file.shardIndex}}</td>
        <td>{{file.shardSize}}</td>
        <td>{{file.shardTotal}}</td>
        <td>{{file.key}}</td>
        <td>{{file.vod}}</td>
      </tr>
      </tbody>
    </table>
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
    }
  }
}
</script>