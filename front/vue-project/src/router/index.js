import { createRouter, createWebHistory } from 'vue-router';
import { useUserRoleStore } from '@/stores/token.js';

// 导入组件
import LoginVue from '@/views/Login.vue';
import LayoutVue from '@/views/Layout.vue';
import DoctorManagementVue from '@/views/DoctorsManagement.vue';
import pwdResetVue from '@/views/pwdReset.vue';
import DocAppointmentManage from '@/views/DocAppointmentManagement.vue';
import dDocAppointmentVue from '@/views/dDocAppointment.vue';
import patientManagementVue from '@/views/patientManagement.vue';
import appointmentVue from '@/views/appointment.vue';
import registrationVue from '@/views/registration.vue';
import uAppointmentVue from '@/views/uAppointment.vue';
import dAppointmentVue from '@/views/dAppointment.vue';
import uRegistraionVue from '@/views/uRegistraion.vue';
import dRegistrationVue from '@/views/dRegistration.vue';
import uLayoutVue from '@/views/uLayout.vue';
import dLayoutVue from '@/views/dLayout.vue';
import dpwdReset from '@/views/dpwdReset.vue';
import upwdReset from '@/views/upwdReset.vue';
import quitVue from '@/views/quit.vue'



import dateView from '@/views/dateView.vue';

// 定义路由关系
const routes = [
    { path: '', redirect:'/login' },
    { path: '/login', component: LoginVue },
    {
        path: '/admin',
        component: LayoutVue,
        children: [
            { path: '', redirect: '/doctorManagement' }, // Default route after login
            { path: '/doctorManagement', component: DoctorManagementVue, meta: { requiresRole: '1' } },
            { path: '/pwdReset', component: pwdResetVue },
            { path: '/docAppointmentManagement', component: DocAppointmentManage, meta: { requiresRole: '1' } },
            { path: '/patientManagement', component: patientManagementVue, meta: { requiresRole: '1' } },
            { path: '/appointment', component: appointmentVue , meta: { requiresRole: '1' }},
            { path: '/registration', component: registrationVue , meta: { requiresRole: '1' }},
            { path: '/dataview', component: dateView , meta: { requiresRole: '1' }},
            { path: '/quitVue1', component: quitVue , meta: { requiresRole: '1' }},


        ]
    },
    {
        path: '/patient',
        component: uLayoutVue,
        children: [
            { path: '', redirect: '/uappointment' }, // Default route after login
            { path: '/pwdReset1', component: pwdResetVue },
            { path: '/uappointment', component: uAppointmentVue ,meta: { requiresRole: '3' }},
            { path: '/uregistration', component: uRegistraionVue ,meta: { requiresRole: '3' }},
            { path: '/quitVue3', component: quitVue , meta: { requiresRole: '3' }},

        ]
    },
    {
        path: '/doctor',
        component: dLayoutVue,
        children: [
            { path: '', redirect: '/ddocAppointment' }, // Default route after login
            { path: '/ddocAppointment', component: dDocAppointmentVue ,meta: { requiresRole: '2' }},
            { path: '/pwdReset2', component: pwdResetVue },
            { path: '/dappointment', component: dAppointmentVue ,meta: { requiresRole: '2' }},
            { path: '/dregistration', component: dRegistrationVue ,meta: { requiresRole: '2' }},
            { path: '/quitVue2', component: quitVue , meta: { requiresRole: '2' }},

        ]
    },
    
];

// 创建路由器
const router = createRouter({
    history: createWebHistory(),
    routes: routes
});

router.beforeEach((to, from, next) => {
    const userStore = useUserRoleStore();
    const role = userStore.role;
    // 检查路由是否需要角色权限
    const requiredRole = to.meta.requiresRole;

    if (requiredRole && role !== requiredRole) {
        console.log("您没有访问成功哟")
        console.log("requiredrole",requiredRole)
        console.log("your role",role);
        
        next('/login'); // 如果用户角色不符合要求，重定向到登录页面
    } else {
        next(); // 允许访问目标路由
    }
});

// 导出路由
export default router;

