import Vue from "vue"
import Router from "vue-router"
import Login from "./views/login.vue"
import Admin from "./views/admin"
import Welcome from "@/views/admin/welcome";
import Chapter from "@/views/admin/chapter"
import Section from "@/views/admin/section";
import Course from "@/views/admin/course";
import Category from "@/views/admin/category";
import Teacher from "@/views/admin/teacher";
import File from "@/views/admin/file";
import Content from "@/views/admin/content";
import User from "@/views/admin/user.vue";
import Resource from "@/views/admin/resource.vue";
import Role from "@/views/admin/role.vue";

Vue.use(Router);

export default new Router({
    mode: "history",
    base: process.env.BASE_URL,
    routes: [{
        path: "*",
        redirect: "/login",
    }, {
        path: "",
        redirect: "/login",
    }, {
        path: "/login",
        component: Login
    }, {
        path: "/admin",
        name: "admin",
        component: Admin,
        meta: {
            loginRequire: true
        },
        children: [{
            path: "welcome",
            name: "welcome",
            component: Welcome
        }, {
            path: "business/chapter",
            name: "business/chapter",
            component: Chapter,
        }, {
            path: "business/section",
            name: "business/section",
            component: Section,
        }, {
            path: "business/course",
            name: "business/course",
            component: Course,
        }, {
            path: "business/content",
            name: "business/content",
            component: Content,
        }, {
            path: "business/category",
            name: "business/category",
            component: Category,
        }, {
            path: "business/teacher",
            name: "business/teacher",
            component: Teacher,
        }, {
            path: "file/file",
            name: "file/file",
            component: File,
        }, {
            path: "system/user",
            name: "system/user",
            component: User,
        }, {
            path: "system/resource",
            name: "system/resource",
            component: Resource,
        }, {
            path: "system/role",
            name: "system/role",
            component: Role,
        }]
    }]
})
