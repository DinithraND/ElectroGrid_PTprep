/**
 * 
 */

 $(document).ready(function()
 {
    if ($("#alertSuccess").text().trim() == "")
    {
        $("#alertSuccess").hide();
    }
    $("#alertError").hide();
 });

 $(document).on("click", "#btnSave", function(event)
{
    // Clear alerts---------------------
    $("#alertSuccess").text("");
    $("#alertSuccess").hide();
    $("#alertError").text("");
    $("#alertError").hide();

    // Form validation-------------------
    var status = validateBillForm();
    if (status != true)
    {
        $("#alertError").text(status);
        $("#alertError").show();
    return;
    }

    // If valid------------------------
    $("#formBill").submit();

    var type = ($("#hidBillIDSave").val() == "") ? "POST" : "PUT";

    $.ajax(
        {
         url : "BillsAPI",
         type : type,
         data : $("#formBill").serialize(),
         dataType : "text",
         complete : function(response, status)
         {
         onItemSaveComplete(response.responseText, status);
         }
        });

});

$(document).on("click", ".btnUpdate", function(event)
{
    $("#hidBillIDSave").val($(this).data("billID"));
    $("#userID").val($(this).closest("tr").find('td:eq(0)').text());
    $("#billID").val($(this).closest("tr").find('td:eq(1)').text());
    $("#userAccountNo").val($(this).closest("tr").find('td:eq(2)').text());
    $("#usageAmnt").val($(this).closest("tr").find('td:eq(3)').text());

    $(document).on("click", ".btnRemove", function(event)
    {
        $.ajax(
        {
            url : "ItemsAPI",
            type : "DELETE",
            data : "itemID=" + $(this).data("itemid"),
            dataType : "text",
            complete : function(response, status)
            {
                onItemDeleteComplete(response.responseText, status);
            }
        });
    });
});

// CLIENT-MODEL================================================================
function validateItemForm()
{
    // CODE
    if ($("#userID").val().trim() == "")
    {
        return "Insert User ID.";
    }

    // NAME
    if ($("#billID").val().trim() == "")
    {
        return "Insert Bill ID.";
    }

    // PRICE-------------------------------
    if ($("#userAccountNo").val().trim() == "")
    {
        return "Insert Account Number.";
    }

    // DESCRIPTION------------------------
    if ($("#usageAmnt").val().trim() == "")
    {
        return "Insert Usage Amount.";
    }
    return true;
}

function onItemSaveComplete(response, status)
{
    var resultSet = JSON.parse(response);
    if (resultSet.status.trim() == "success")
    {
        $("#alertSuccess").text("Successfully saved.");
        $("#alertSuccess").show();
        $("#divBillsGrid").html(resultSet.data);
    } else if (resultSet.status.trim() == "error")
    {
        $("#alertError").text(resultSet.data);
        $("#alertError").show();
    }
    else if (status == "error")
    {
        $("#alertError").text("Error while saving.");
        $("#alertError").show();
    } else
    {
        $("#alertError").text("Unknown error while saving..");
        $("#alertError").show();
    }
    $("#hidBillIDSave").val("");
    $("#formBill")[0].reset();
}