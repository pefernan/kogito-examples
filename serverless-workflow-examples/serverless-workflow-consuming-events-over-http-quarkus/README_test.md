# Kogito Serverless Workflow - Consuming Events Over HTTP Example

## Description
This example is an extended version of the original `Consuming Events Over HTTP Example` providing two more SW files 
to test different features in Dev UI.

## Features to test
### Cloud Event form page
It can be accessed from the Workflow List to help you interacting with active instances or from the Definitions page to start new instances.
This screen has been redesigned to allow user fill the needed data to trigger the cloud events via HTTP (no kafka yet).

The main fields are:
- Endpoint: method & url to post the cloud event to
- Event Type: Type of cloud event to be triggered
- Event Source: header specifying the event source
- Instance Id: fill it with the id of an active SW instance (only visible when accessing from the SW List)
- Business Key: fill it with a business key to start a new SW instance (only visible when accessing from the SW Definitions List)
- Custom Headers: Allows adding custom headers (key-value pairs) to the cloud event
- Event Data: fill it with the JSON data you want to send in the cloud event

### Workflow Form page
This screen was intended to be used when starting an instance of a SW via REST, it can be accessed from the Definitions page by clicking on the play
button next to any SW in the table.
This screen has been reworked to enable displaying a custom form (if the SW has a `dataInputSchema` defined) or a CodeEditor
to help the user typing the payload to start the WF instance.

Previously this screen was trying to post a CloudEvent if no form was defined, which was wrong since not all our examples 
accept CloudEvents as start.

## Running the example

Since this example is to test Dev UI capabilities, just start the example in dev mode like:

```sh
mvn clean package quarkus:dev
```

Make sure docker is up and running!
 
When everything is up, access the Dev UI on 'http://localhost:8080/q/dev', go into the 'Kogito Serverless Workflow Tools'
extension UI and open the Process Definitions tab to start the SW.

## The workflows
All the workflows in this example are basically the same as the main example but differ on how they are started.

The available workflows are:
- startRESTNoForm: The original SW in the example. It should be started via rest and no JSON schema is provided, so Dev UI
shows the CodeEditor so the user types the Payload in Json format there. 
It should be started by clicking the play button on the Process Definitions List.

- startRESTWithForm: It starts via REST, but it has a JSON schema configured in the `dataInputSchema` property, so the Dev UI
will display a regular Form with two fields instead of CodeEditor. 
Again, it should be started by clicking the play button on the Process Definitions List.

- startWithCloudEvent: This one is intended to be started via CE, to do so, you should Click on the `Trigger Cloud Event` button
in the Process Definitions List, this will bring you to the Cloud Event form page.
Fill the form with the followoing data:

endpoint: 
- method: POST
- url: /startevent 
event type: start
source: not required
business key: whatever
custom headers: not required
event data: 
```json
{
  "message": "Hello"
}
```

After pressing Trigger go to WF List to see if the instance has been successfully started.

## Moving the Workflows Forward

After started the WF should appear in a `Active` state in the WF List page. As explained in the example, they require a `move`
event to move forward. There are two ways to trigger the event from Dev UI:

- Open the actions kebab on any of the SW instance and press `Send Cloud Event`. This will forward you to the Cloud Event page with the Instance Id field already filled.
  (The kebab is only enabled if the kogito project has the process management addon)
- Click on the Trigger Cloud Event button, and you'll be forwarded to the Cloud Event page... you'll need to know the SW instance Id to fill the form.

In there you should fill the form with the following data:
endpoint:
- method: POST
- url: / (move event has to be posted to the root path)
event type: move
source: not required
instance id: already provided if you came via kebab, if not you should type there the ID
custom headers: not required 
event data:
```json
{
  "move":"This has been injected by the event"
}
```

And Press trigger button to trigger the cloud event... if everything went well the WF List should display the workflow in a complete status.

