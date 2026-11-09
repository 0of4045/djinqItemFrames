# djinqItemFrames

A small and lightweight item frame toggle plugin for Paper servers.

Look at an item frame and use `/itemframe` to make only that frame visible or invisible. The item inside stays visible, while the frame itself is toggled.

Made by djinq ~ [djinq.dev](https://djinq.dev)

## Installation

1. Drop `djinqItemFrames.jar` into your server's `plugins` folder.
2. Start the server once.
3. Edit `plugins/djinqItemFrames/config.yml` and `messages.yml` however you want.
4. Restart the server.

## Commands

| Command | Description |
| --- | --- |
| `/itemframe` | Toggles the item frame you are looking at. |

## Permissions

| Permission | Description |
| --- | --- |
| `djinqitemframes.use` | Allows `/itemframe`. |

## Configuration

In `config.yml`, you can edit:

- `target-distance` ~ the maximum distance for the targeted item frame.

`messages.yml` contains one `prefix` setting. It is added automatically to every chat message.

Built for Paper 1.21.11 through 26.3. Use Java 21 for Paper 1.21.11, or Java 25 for Paper 26.1+.
