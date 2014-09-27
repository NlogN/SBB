$(document).ready(function () {
    $(".form").validate({
        rules: {
            'addTrainForm:number': {
                required: true,
                minlength: 1,
                maxlength: 5
            },
            'addTrainForm:capacity': {
                required: true,
                minlength: 1,
                maxlength: 5
            },
            'viewPassengersForm:num': {
                required: true,
                minlength: 1,
                maxlength: 5
            },
            'addStationForm:name': {
                required: true,
                minlength: 1,
                maxlength: 10
            },
            'addStationScheduleForm:name': {
                required: true,
                minlength: 1,
                maxlength: 10
            },
            'addStationScheduleForm:num': {
                required: true,
                minlength: 1,
                maxlength: 5
            }
        },

        messages: {
            'addTrainForm:number': {
                required: "enter train num!",
                minlength: "min length is 1 character",
                maxlength: "max length is 5 characters"
            },
            'addTrainForm:capacity': {
                required: "enter train capacity!",
                minlength: "min length is 1 character",
                maxlength: "max length is 5 characters"
            },
            'viewPassengersForm:num': {
                required: "enter train num!",
                minlength: "min length is 1 character",
                maxlength: "max length is 5 characters"
            },
            'addStationForm:name': {
                required: "enter station name!",
                minlength: "min length is 1 character",
                maxlength: "max length is 10 characters"
            },
            'addStationScheduleForm:name': {
                required: "enter train num!",
                minlength: "min length is 1 character",
                maxlength: "max length is 10 characters"
            },
            'addStationScheduleForm:num': {
                required: "enter station name!",
                minlength: "min length is 1 character",
                maxlength: "max length is 5 characters"
            }
        },

        submitHandler: function (form) {
            form.submit();
        }
    });


});
