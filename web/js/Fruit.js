function deleteFruit(name) {
    if (confirm("是否确认删除?")) {
        window.location.href = "fruit?operate=delete.do&name=" + name;
    }
}
