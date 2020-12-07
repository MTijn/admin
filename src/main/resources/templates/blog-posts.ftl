<!DOCTYPE html>
<html lang="en">
<head>
    <title>Home page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-CuOF+2SnTUfTwSZjCXf01h7uYhfOBuxIhGKPbfEJ3+FqH/s6cIFN9bGr1HmAg4fQ" crossorigin="anonymous">
    <link href="admin.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-popRpmFF9JQgExhfw5tZT4I9/CI5e2QcuUZPOVXb1m7qUmeR2b50u+YFEYe1wgzy" crossorigin="anonymous"></script>
</head>
<body>
<#include "header.ftl">
<div class="container-fluid">
    <div class="row">
        <#include "sidebar.ftl">
        <main class="col-md-9 ml-sm-auto col-lg-10 px-md-4">
            <h1>Blog posts</h1>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Tags</th>
                        <th>Created at</th>
                    </tr>
                </thead>
                <tbody>
                    <#list blogPosts as blogPost>
                        <tr>
                            <td>${blogPost.title}</td>
                            <td>${blogPost.tags}</td>
                            <td>${blogPost.createdAt.format('dd-MM-yyyy HH:mm:ss Z')}</td>
                        </tr>
                    </#list>
                </tbody>
            </table>
        </main>
    </div>
</div>
</body>
</html>
