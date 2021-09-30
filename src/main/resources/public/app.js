var index = (function (){
        function change(entrada) {
            const body = $("tbody");
            if (body !== null) {
                body.remove();
            }
            if (entrada != null && entrada != "") {
                agregarText(entrada).then(function(data){
                    getText(data)
                });
            }
        }

        function agregarText(entrada) {
            return $.ajax({
                url: "/amazondocker/entra",
                type: "POST",
                data: entrada,
                contentType: "application/json",
            });
        }

        function getText(data){
            console.log(data);
            console.log(data[0]);
            const tabla = $("table");
            const body = $("tbody");
            if (body != null) {
                body.remove();
            }
            tabla.append("<tbody>");
            const tblBody = $("tbody");
            var columnas="";
            for(const property in data){
                columnas=columnas+'<tr>'
                columnas=columnas+"<td>"+data[property].text+"</td>";
                console.log(data[property].text);
                columnas=columnas+"<td>"+data[property].date+"</td>";
                columnas=columnas+"</tr>";
            }
            tblBody.append(columnas);
            tabla.append("</tbody>");
        }

        return {
            change: change,
            agregarText: agregarText,
            getText: getText
        }

    }
)()