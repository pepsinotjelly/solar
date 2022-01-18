<template>
<div>
  <h1>
    "i am another page, juju! "
  </h1>
  <a> 作者是 {{ author }} </a>
  <p>
    这是
    <a href="https://github.com/pepsinotjelly">pepsinotjelly</a>
    写的demo用来测试route
  </p>
</div>
</template>

<script>
export default {
  name: "secondcomponent",
  data() {
    return {
      author: "sonia suen",
      articles: [],
    }
  }
}
</script>

<style scoped>
</style>
