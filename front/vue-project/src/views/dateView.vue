<template>
    <div class="chart-container">
      <div class="chart-wrapper">
        <pie-chart :data="appointmentsChartData" :options="chartOptions" />
        <p>Appointments States</p>
      </div>
      <div class="chart-wrapper">
        <pie-chart :data="registrationsChartData" :options="chartOptions" />
        <p>Registrations States</p>
      </div>
    </div>
  </template>
  
  <script>
  import { ref, computed } from 'vue';
  import { Pie } from 'vue-chartjs';
  import { Chart as ChartJS, Title, Tooltip, Legend, ArcElement } from 'chart.js';
  import { appointmentListService } from '@/api/appointment';
  import { registrationListService } from '@/api/registration'; // 假设有一个 registrationListService
  
  // 初始化数据
  const appointments = ref([]);
  const registrations = ref([]);
  const searchForm = ref({ patientNum: null });
  
  const getAppointmentList = async () => {
    let result = await appointmentListService(searchForm.value);
    appointments.value = result.data;
  };
  const getRegistrationList = async () => {
    let result = await registrationListService(searchForm.value);
    registrations.value = result.data;
  };
  
  getAppointmentList();
  getRegistrationList();
  
  // 注册 Chart.js 插件
  ChartJS.register(Title, Tooltip, Legend, ArcElement);
  
  export default {
    components: { PieChart: Pie },
  
    setup() {
      const appointmentsChartData = computed(() => {
        const stateCounts = appointments.value.reduce((acc, appointment) => {
          const state = appointment.state || '未完成或异常';
          acc[state] = (acc[state] || 0) + 1;
          return acc;
        }, {});
  
        return {
          labels: Object.keys(stateCounts),
          datasets: [{
            label: 'Appointments States',
            data: Object.values(stateCounts),
            backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56'], // 你可以根据需要自定义颜色
          }]
        };
      });
  
      const registrationsChartData = computed(() => {
        const stateCounts = registrations.value.reduce((acc, registration) => {
          const state = registration.state || '未完成或异常';
          acc[state] = (acc[state] || 0) + 1;
          return acc;
        }, {});
  
        return {
          labels: Object.keys(stateCounts),
          datasets: [{
            label: 'Registrations States',
            data: Object.values(stateCounts),
            backgroundColor: ['#FF9F40', '#FFCD56', '#4BC0C0'], // 自定义颜色
          }]
        };
      });
  
      const chartOptions = {
        responsive: true,
        plugins: {
          legend: {
            position: 'top',
          },
          tooltip: {
            callbacks: {
              label: (tooltipItem) => {
                return `${tooltipItem.label}: ${tooltipItem.raw} records`;
              }
            }
          }
        }
      };
  
      return {
        appointmentsChartData,
        registrationsChartData,
        chartOptions
      };
    }
  };
  </script>
  
  <style>
  .chart-container {
    display: flex; /* 使用 flexbox 布局来并排显示图表 */
    justify-content: space-around; /* 在容器中均匀分配图表 */
    align-items: center; /* 垂直居中对齐图表 */
    flex-wrap: wrap; /* 允许图表换行，以适应较小的屏幕 */
    gap: 20px; /* 控制图表之间的间距 */
  }
  
  .chart-wrapper {
    width: 45%; /* 控制每个图表的宽度 */
    height: 300px; /* 控制每个图表的高度 */
  }
  
  p {
    text-align: center; /* 图表下方的标题居中 */
    margin-top: 10px; /* 标题与图表之间的间距 */
  }
  </style>
  