# frozen_string_literal: true

class Document < ApplicationRecord
  belongs_to :user
  scope :favorites, -> { where(favorite: true) }
  scope :about_to_expire, -> { where('expiration_date <= ?', 10.days.from_now) }
  has_one_attached :file
  acts_as_taggable_on :tags

  after_update do
    Notification.create(heading: 'Documento Guardado', content: "#{name.gsub("\n", '').titleize} ha sido almacenado exitosamente.",
                        data: self, user:)
  end
end
