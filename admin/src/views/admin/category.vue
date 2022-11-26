<template>
  <div>
    <div class="row">
      <div class="col-md-6">
        <p>
          <button v-on:click="add1()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-edit"></i>
            新增一级
          </button>
          &nbsp;
          <button v-on:click="categoryList()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-refresh"></i>
            刷新
          </button>
        </p>
        <table id="category-table" class="table  table-bordered table-hover">
          <thead>
          <tr>
            <th>id</th>
            <th>名称</th>
            <th>顺序</th>
            <th>操作</th>
          </tr>
          </thead>

          <tbody>
          <tr v-for="category in categories" v-on:click="subCategoryList(category)"
              v-bind:class="{'active' : category.id === active.id}">
            <td>{{ category.id }}</td>
            <td>{{ category.name }}</td>
            <td>{{ category.sort }}</td>
            <td>
              <div class="hidden-sm hidden-xs btn-group">
                <button class="btn btn-xs btn-info" v-on:click="edit(category)">
                  <i class="ace-icon fa fa-pencil bigger-120"></i>
                </button>
                <button class="btn btn-xs btn-danger" v-on:click="del(category)">
                  <i class="ace-icon fa fa-trash-o bigger-120"></i>
                </button>
              </div>

              <div class="hidden-md hidden-lg">
                <div class="inline pos-rel">
                  <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown"
                          data-position="auto">
                    <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                  </button>

                  <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                    <li>
                      <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit" v-on:click="edit(category)">
                     <span class="green">
                       <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                     </span>
                      </a>
                    </li>
                    <li>
                      <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" v-on:click="del(category)">
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
      </div>
      <div class="col-md-6">
        <p>
          <button v-on:click="add2()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-edit"></i>
            新增二级
          </button>
        </p>
        <table id="child-table" class="table  table-bordered table-hover">
          <thead>
          <tr>
            <th>id</th>
            <th>名称</th>
            <th>顺序</th>
            <th>操作</th>
          </tr>
          </thead>

          <tbody>
          <tr v-for="category in childCategoryList">
            <td>{{ category.id }}</td>
            <td>{{ category.name }}</td>
            <td>{{ category.sort }}</td>
            <td>
              <div class="hidden-sm hidden-xs btn-group">
                <button class="btn btn-xs btn-info" v-on:click="edit(category)">
                  <i class="ace-icon fa fa-pencil bigger-120"></i>
                </button>
                <button class="btn btn-xs btn-danger" v-on:click="del(category.id)">
                  <i class="ace-icon fa fa-trash-o bigger-120"></i>
                </button>
              </div>

              <div class="hidden-md hidden-lg">
                <div class="inline pos-rel">
                  <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown"
                          data-position="auto">
                    <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                  </button>

                  <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                    <li>
                      <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit" v-on:click="edit(category)">
                     <span class="green">
                       <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                     </span>
                      </a>
                    </li>
                    <li>
                      <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete" v-on:click="del(category.id)">
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
      </div>
    </div>

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
                <label class="col-sm-2 control-label">父分类</label>
                <div class="col-sm-10">
                  <p class="form-control-static">{{ active.name || "无" }}</p>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                  <input v-model="category.name" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">顺序</label>
                <div class="col-sm-10">
                  <input v-model="category.sort" class="form-control">
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
  name: "business-category",
  data: function () {
    return {
      categories: [],
      category: {},
      childCategoryList: [],
      active: {}
    }
  },
  mounted: function () {
    let _this = this;
    _this.categoryList();
    // sidebar激活样式方法一
    // this.$parent.activeSidebar("business-category-sidebar");
  },
  methods: {
    /**
     * 点击【新增一级】
     */
    add1() {
      let _this = this;
      _this.active = {};
      _this.level2 = [];
      _this.category = {
        parent: "00000000"
      };
      $("#form-modal").modal("show");
    },

    /**
     * 点击【新增二级】
     */
    add2() {
      let _this = this;
      if (Tool.isEmpty(_this.active)) {
        Toast.warning("请先点击一级分类");
        return;
      }
      _this.category = {
        parent: _this.active.id
      };
      $(".modal").modal("show");
    },

    /**
     * 点击【编辑】
     */
    edit(category) {
      let _this = this;
      _this.category = $.extend({}, category);
      $("#form-modal").modal("show");
    },

    /**
     * 列表查询
     */
    list(page) {
      let _this = this;
      Loading.show();
      _this.$axios.get(process.env.VUE_APP_SERVER + '/business/admin/category/list', {
        params: {
          page: page,
          size: _this.$refs.pagination.size
        }
      }).then((response) => {
        Loading.hide();
        console.log("查询商品分类 列表结果：", response);
        _this.categories = response.data.data.list;
        _this.$refs.pagination.render(page, response.data.data.total);
      })
    },

    /**
     * 列表查询
     */
    categoryList() {
      let _this = this;
      Loading.show();
      _this.$axios.get(process.env.VUE_APP_SERVER + '/business/admin/category/categoryList').then((response) => {
        Loading.hide();
        console.log("查询商品分类 列表结果：", response);
        _this.categories = response.data.data;
        if (Tool.isNotEmpty(_this.active)) {
          for (let i = 0; i < _this.categories.length; i++) {
            if (_this.categories[i].id === _this.active.id) {
              _this.childCategoryList = _this.categories[i].childCategoryList;
            }
          }
        }
      })
    },

    /**
     * 点击【保存】
     */
    save() {
      let _this = this;

      // 保存校验
      if (1 !== 1
          || !Validator.require(_this.category.parent, "父id")
          || !Validator.require(_this.category.name, "名称")
          || !Validator.length(_this.category.name, "名称", 1, 50)
      ) {
        return;
      }

      Loading.show();
      _this.$axios.post(process.env.VUE_APP_SERVER + '/business/admin/category/save', _this.category).then((response) => {
        Loading.hide();
        console.log("保存商品分类 列表结果：", response);
        if (response.data.code === "0") {
          $("#form-modal").modal("hide");
          _this.categoryList();
          Toast.success("保存成功！");
        } else {
          Toast.warning(response.data.message);
        }
      })
    },

    /**
     * 点击【删除】
     */
    del(category) {
      let _this = this;
      Confirm.show("删除后将不可恢复，确认删除吗？", function () {
        Loading.show();
        _this.$axios.delete(process.env.VUE_APP_SERVER + '/business/admin/category/delete/' + category.id).then((response) => {
          Loading.hide();
          console.log("删除商品分类 列表结果：", response);
          if (response.data.code === "0") {
            if (category.parent === "00000000") {
              _this.childCategoryList = [];
            }
            _this.categoryList();
            Toast.success("删除成功！")
          }
        })
      });
    },

    subCategoryList(category) {
      let _this = this;
      console.log(category.name + "子类为", category.childCategoryList)
      _this.childCategoryList = category.childCategoryList;
      _this.active = category;
    }
  }
}
</script>

<style scoped>
.active td {
  background-color: #a4ccff !important;
}
</style>