<template>
  <div>
    <div id = "radarChart" :style="{width: '600px',height: '800px'}"></div>
  </div>


</template>

<script>
export default {
  name: "radarcomponent",
  data() {
    return {}
  },
  methods: {
    drawline() {
      let radarChart = this.$echarts.init(document.getElementById('radarChart'));
      let dataMax = [
        {name: '年龄群体', max: '100'},
        {name: '消费口味', max: '100'},
        {name: '我说什么好', max: '100'},
        {name: '爱咋咋吧', max: '100'},
        {name: '最后一点', max: '100'}
      ]
      let option = {
        radar: {
          name: {
            show: true,
            color: 'rgba(255,0,0,0)'
          },
          indicator: dataMax,
          shape: 'circle'
        },
        series:[
          {
          type: 'radar',
          label: {
            show: true, // 显示数值
          },
          areaStyle: {}, //每个radar形成的阴影的面积
          data: [
            {
              name: '用户A',
              value: [80, 83, 90, 89, 33]
            },
            {
              name: '小仓',
              value: [89, 90, 29, 48, 29]
            },
          ],
        },
        ],
      }
        radarChart.setOption(option)
    },
  },
  mounted() {
    this.drawline()
  },
}
</script>

<style scoped>

</style>
