<template>
  <div>
    <p>
      <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
    </p>
    <div class="row">
      <div class="col-md-6">
        <textarea id="resource-textarea" class="form-control" v-model="resourceStr" name="resource" rows="10"></textarea>

        <br>
        <button id="save-btn" type="button" class="btn btn-primary" v-on:click="save()">
          保存
        </button>
      </div>
      <div class="col-md-6">
        <ul id="tree" class="ztree"></ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "system-resource",
  data: function () {
    return {
      resources: [],
      resource: {},
      resourceStr: "",
      tree: {},
    }
  },
  mounted: function () {
    let _this = this;
    _this.list();
    // sidebar激活样式方法一
    // this.$parent.activeSidebar("system-resource-sidebar");
  },
  methods: {

    /**
     * 列表查询
     */
    list() {
      let _this = this;
      Loading.show();
      _this.$axios.get(process.env.VUE_APP_SERVER + '/system/admin/resource/load-tree').then((response) => {
        Loading.hide();
        _this.resources = response.data.data
        // 初始化树
        _this.initTree();
      })
    },

    /**
     * 点击【保存】
     */
    save() {
      let _this = this;

      // 保存校验
      if (Tool.isEmpty(_this.resourceStr)) {
        Toast.warning("资源不能为空！");
        return;
      }
      let json = JSON.parse(_this.resourceStr);

      Loading.show();
      _this.$axios.post(process.env.VUE_APP_SERVER + '/system/admin/resource/save', json).then((response) => {
        Loading.hide();
        console.log("保存资源列表结果：", response);
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
        _this.$axios.delete(process.env.VUE_APP_SERVER + '/system/admin/resource/delete/' + id).then((response) => {
          Loading.hide();
          console.log("删除资源列表结果：", response);
          if (response.data.code === "0") {
            _this.list(1);
            Toast.success("删除成功！")
          }
        })
      });
    },
    /**
     * 初始资源树
     */
    initTree() {
      let _this = this;
      let setting = {
        data: {
          simpleData: {
            idKey: "id",
            pIdKey: "parent",
            rootPId: "",
            // enable: true
          }
        }
      };

      _this.zTree = $.fn.zTree.init($("#tree"), setting, _this.resources);
      _this.zTree.expandAll(true);
    },
  }
}
</script>