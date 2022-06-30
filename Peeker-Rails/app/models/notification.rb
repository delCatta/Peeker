# frozen_string_literal: true

require 'Notifier/Push'
class Notification < ApplicationRecord
  belongs_to :user

  after_create do
    Notifier::Push.send_push(user.id, heading, subtitle, content, data)
  end
end
