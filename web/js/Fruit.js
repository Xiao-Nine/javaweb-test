function deleteFruit(name) {
    if (confirm("是否确认删除?")) {
        window.location.href = "fruit.do?operate=deleteFruit&name=" + name;
    }
}
