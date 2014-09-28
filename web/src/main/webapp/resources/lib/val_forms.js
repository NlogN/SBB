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
                maxlength: 15
            },
            'addStationScheduleForm:name': {
                required: true,
                minlength: 1,
                maxlength: 15
            },
            'addStationScheduleForm:num': {
                required: true,
                minlength: 1,
                maxlength: 5
            },
            'searchTrainForm:stationA': {
                required: true,
                minlength: 1,
                maxlength: 15
            },
            'searchTrainForm:stationB': {
                required: true,
                minlength: 1,
                maxlength: 15
            },
            'buyTicketForm:name': {
                required: true,
                minlength: 1,
                maxlength: 15
            },
            'buyTicketForm:surname': {
                required: true,
                minlength: 1,
                maxlength: 15
            },
            'buyTicketForm:num': {
                required: true,
                minlength: 1,
                maxlength: 5
            },
            'buyTicketForm:station': {
                required: true,
                minlength: 1,
                maxlength: 15
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
                maxlength: "max length is 15 characters"
            },
            'addStationScheduleForm:name': {
                required: "enter train num!",
                minlength: "min length is 1 character",
                maxlength: "max length is 15 characters"
            },
            'addStationScheduleForm:num': {
                required: "enter station name!",
                minlength: "min length is 1 character",
                maxlength: "max length is 5 characters"
            },
            'searchTrainForm:stationA': {
                required: "enter station name!",
                minlength: "min length is 1 character",
                maxlength: "max length is 15 characters"
            },
            'searchTrainForm:stationB': {
                required: "enter station name!",
                minlength: "min length is 1 character",
                maxlength: "max length is 5 characters"
            },
            'buyTicketForm:name': {
                required: "enter name!",
                minlength: "min length is 1 character",
                maxlength: "max length is 15 characters"
            },
            'buyTicketForm:surname': {
                required: "enter surname!",
                minlength: "min length is 1 character",
                maxlength: "max length is 15 characters"
            },
            'buyTicketForm:num': {
                required: "enter train number!",
                minlength: "min length is 1 character",
                maxlength: "max length is 5 characters"
            },
            'buyTicketForm:station': {
                required: "enter station name!",
                minlength: "min length is 1 character",
                maxlength: "max length is 15 characters"
            }
        },

        submitHandler: function (form) {
            form.submit();
        }
    });


});
