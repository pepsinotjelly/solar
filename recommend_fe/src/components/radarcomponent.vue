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
    initRadar() {
      let radarChart = this.$echarts.init(document.getElementById('radarChart')); // 初始化chart
      let dataMax = [
        {name: '年龄群体', max: '100'},
        {name: '消费口味', max: '100'},
        {name: '我说什么好', max: '100'},
        {name: '爱咋咋吧', max: '100'},
        {name: '最后一点', max: '100'}
      ]
      let option = {
        calculable : true,
        radar: {
          name: {
            formatter: [`{a|${this.type}}`,
              '{b|{value}}'].join('\n'),
            rich: {
              a: {
                color: 'rgba(5, 193, 174, 1)',
                align: 'middle'
              },
              b: {
                color: '#333'
              },
            },
            show: true,
            color: 'rgba(255,0,0,0)'
          },
          indicator: dataMax,
          shape: 'circle',
          splitNumber: 5
        },
        title: {
          text: "你和小仓的相似之处",
          left: 'center',
          textStyle: {
            fontSize: 16,
            fontWeight: 'bolder',
          },
        },
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
          orient: "vertical",
          left: "left",
          data: ["年龄群体", "消费口味", "我说什么好", "爱咋咋吧", "最后一点"]
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
              value: [89, 90, 79, 88, 29]
            },
          ],
        },
        ],
      }
        radarChart.setOption(option)
    },
  },
  mounted() {
    this.initRadar()
  },
}
</script>

<style scoped>

</style>
